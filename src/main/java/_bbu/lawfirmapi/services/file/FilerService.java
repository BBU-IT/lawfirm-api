package _bbu.lawfirmapi.services.file;

import _bbu.lawfirmapi.models.File.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FilerService {

    String uploadPdfFile(MultipartFile file) throws Exception;
    List<String> uploadMultipleFilePdf(List<MultipartFile> files) throws  Exception;
    String getPdfPreviewUrl(String objectName) throws  Exception;
    FileMetaData uploadFile(MultipartFile file);

    InputStream getFileByFileName(String fileName);

    List<FileMetaData> bulkUploadFile(List<MultipartFile> files);

    List<String> getAllImagesUrl();
}
