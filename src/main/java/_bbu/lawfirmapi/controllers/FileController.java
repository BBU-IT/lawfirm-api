package _bbu.lawfirmapi.controllers;

import java.io.InputStream;
import java.util.List;

import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.File.FileMetaData;
import _bbu.lawfirmapi.services.file.FilerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController extends BaseResponse {
    private final FilerService fileService;

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileMetaData>> uploadFile(
            @RequestParam("file") MultipartFile file) {

        ApiResponse<FileMetaData> apiResponse = ApiResponse.<FileMetaData>builder().success(true)
                .message("File uploaded successfully! Metadata of the uploaded file is returned.")
                .status(HttpStatus.CREATED).code(HttpStatus.OK.value())
                .payload(fileService.uploadFile(file)).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping(value = "/upload-file/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<FileMetaData>>> bulkUploadFile(
            @RequestParam("files") List<MultipartFile> files) {

        List<FileMetaData> uploadedFiles = fileService.bulkUploadFile(files);

        ApiResponse<List<FileMetaData>> apiResponse =
                ApiResponse.<List<FileMetaData>>builder().success(true)
                        .message("File uploaded successfully! Metadata of the uploaded file is returned.")
                        .status(HttpStatus.CREATED).code(HttpStatus.OK.value()).payload(uploadedFiles).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @SneakyThrows
    @GetMapping("/preview-file/{file-name}")
    public ResponseEntity<?> getFileByFileName(@PathVariable("file-name") String fileName) {

        InputStream inputStream = fileService.getFileByFileName(fileName);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        if (fileName.endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (fileName.endsWith(".svg")) {
            mediaType = MediaType.valueOf("image/svg+xml");
        } else if (fileName.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType)
                .body(inputStream.readAllBytes());
    }
    @SneakyThrows
    @GetMapping("/download-file/{file-name}")
    public ResponseEntity<byte[]> downloadFileByFileName(@PathVariable("file-name") String fileName){
        InputStream inputStream = fileService.getFileByFileName(fileName);
        byte[] fileBytes = inputStream.readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        MediaType mediaType = fileName.endsWith(".pdf") ? MediaType.APPLICATION_PDF : MediaType.APPLICATION_OCTET_STREAM;

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(mediaType)
                .contentLength(fileBytes.length)
                .body(fileBytes);
    }
}
