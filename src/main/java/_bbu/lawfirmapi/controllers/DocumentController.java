package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.doc.request.DocRequest;
import _bbu.lawfirmapi.models.DTO.doc.response.DocResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Document;
import _bbu.lawfirmapi.services.doc.DocService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController  extends BaseResponse {

    private final DocService docService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<DocResponse>>> getAllDocuments(){
        return responseEntity(true ,
                "Get all document successfully",
                HttpStatus.OK,
                docService.fetchAllDocs());
    }
    @GetMapping("/{documentId:\\d+}")
    public ResponseEntity<ApiResponse<DocResponse>> getDocById(@PathVariable @Valid @Positive Long documentId){
        return responseEntity(true ,
                "Get document with id " + documentId + " successfully",
                HttpStatus.ACCEPTED,
                docService.fetchDocById(documentId));
    }
    @GetMapping("/by-category")
    public ResponseEntity<ApiResponse<List<DocResponse>>> getDocListWithCategory(@RequestParam String  categoryName){
        return responseEntity(true ,
                "Get document with category " + categoryName.toUpperCase() + " successfully",
                HttpStatus.ACCEPTED,
                docService.fetchDocsByCategoryName(categoryName.toUpperCase()));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<DocResponse>> insertNewDoc(@RequestBody DocRequest docRequest){
        return responseEntity(true ,
                "Create new document successfully",
                HttpStatus.CREATED,
                docService.createNewDocument(docRequest));
    }
    @PutMapping("/{documentId}")
    public ResponseEntity<ApiResponse<DocResponse>> updateDocById(
            @PathVariable @Valid @Positive Long documentId ,
            @RequestBody DocRequest docRequest
    ) {
        return responseEntity(true ,
                "Update document successfully",
                HttpStatus.ACCEPTED,
                docService.modifiedExistDocumentById(documentId , docRequest));
    }
    @DeleteMapping("/{documentId}")
    public ResponseEntity<ApiResponse<Void>> deleteDocById(@PathVariable @Valid @Positive Long documentId){
        return responseEntity(true ,
                "Delete document "+ docService.fetchDocById(documentId).getTitle()  + " successfully",
                HttpStatus.OK,
                docService.removeExistingDocumentById(documentId));
    }
}
