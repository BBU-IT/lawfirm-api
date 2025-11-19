package _bbu.lawfirmapi.services.file.implement;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import _bbu.lawfirmapi.exceptions.InvalidException;
import _bbu.lawfirmapi.models.File.FileMetaData;
import _bbu.lawfirmapi.services.file.FileService;
import io.minio.*;
import io.minio.http.Method;
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
public class FileServiceImplement implements FileService {
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
            String url = "http://localhost:9000/" + bucketName + "/" + fileName;;
            listOfUrls.add(url);
        }
        List<String> pdfOnly = listOfUrls.stream()
                .filter(url -> url.toLowerCase().endsWith(".pdf")).collect(Collectors.toList());

        return pdfOnly;
    }

    @Override
    public List<String> uploadMultipleFilePdf(List<MultipartFile> files) throws Exception {
        List<String> objectNames = new ArrayList<>();

        for(MultipartFile file : files){
            // Generate unique name to avoid collisions
            String objectName = UUID.randomUUID().toString() + ".pdf";

            // Upload each file
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
            objectNames.add(objectName);

        }
        return objectNames;
    }
    @Override
    public String uploadPdfFile(MultipartFile file) throws Exception {
        String objectName = UUID.randomUUID().toString()+".pdf";

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType()) // Sets content type
                        .build());

        return objectName;
    }

    @Override
    public String getPdfPreviewUrl(String objectName) throws Exception {
        // Set response-content-type to application/pdf for inline browser preview
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("response-content-type", "application/pdf");

        // Generate a temporary presigned URL that expires in a short time (e.g., 1 hour)
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(1, TimeUnit.HOURS)
                        .extraQueryParams(reqParams)
                        .build());
        return url;
    }
}
