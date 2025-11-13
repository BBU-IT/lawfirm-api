package _bbu.lawfirmapi.services.file.implement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import _bbu.lawfirmapi.exceptions.InvalidException;
import _bbu.lawfirmapi.models.File.FileMetaData;
import _bbu.lawfirmapi.services.file.FilerService;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class FileServiceImplement implements FilerService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;
    @Value("${minio.url}")
    private String minioUrl;

    private void verifyFileExtension(MultipartFile file) {
        // validate file extension allow only ending with .png, .svg, .jpg, .jpeg, or .gif
        List<String> allowFileExtensions =
                List.of("image/png", "image/svg+xml", "image/jpg", "image/jpeg", "image/gif" , "application /pdf");

        if (!allowFileExtensions.contains(file.getContentType()) || file.getContentType() == null) {
            throw new InvalidException(
                    "Profile image must be a valid image URL ending with .png, .svg, .jpg, .jpeg, or .gif");
        }
    }

    @SneakyThrows
    private FileMetaData uploadFileToMinio(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);

        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
                .contentType(file.getContentType()).stream(file.getInputStream(), file.getSize(), -1)
                .build());

        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/preview-file/" + fileName).toUriString();

        return FileMetaData.builder().fileName(fileName).fileType(file.getContentType())
                .fileUrl(fileUrl).fileSize(file.getSize()).build();
    }

    @SneakyThrows
    @Override
    public FileMetaData uploadFile(MultipartFile file) {
        verifyFileExtension(file);

        boolean bucketExists =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        return uploadFileToMinio(file);
    }

    @SneakyThrows
    @Override
    public InputStream getFileByFileName(String fileName) {

        return minioClient
                .getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    @SneakyThrows
    @Override
    public List<FileMetaData> bulkUploadFile(List<MultipartFile> files) {
        for (MultipartFile multipartFile : files) {
            verifyFileExtension(multipartFile);
        }

        List<FileMetaData> responseFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            responseFiles.add(uploadFileToMinio(file));
        }
        return responseFiles;
    }
    @SneakyThrows
    public List<String> getAllImagesUrl(){
        List<String> listOfUrls = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .build());
        for(Result<Item> result : results ){
            Item item = result.get();
            String fileName = item.objectName();
            String url = String.format("%s/%s", bucketName , fileName);
            listOfUrls.add(url);
        }
        return listOfUrls;
    }
}
