package _bbu.lawfirmapi.services.file;

import _bbu.lawfirmapi.models.File.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FilerService {

    FileMetaData uploadFile(MultipartFile file);

    InputStream getFileByFileName(String fileName);

    List<FileMetaData> bulkUploadFile(List<MultipartFile> files);
}
