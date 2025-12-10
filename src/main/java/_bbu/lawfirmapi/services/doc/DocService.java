package _bbu.lawfirmapi.services.doc;

import _bbu.lawfirmapi.models.DTO.doc.request.DocRequest;
import _bbu.lawfirmapi.models.DTO.doc.response.DocResponse;
import _bbu.lawfirmapi.models.Entity.Document;

import java.util.List;

public interface DocService {

    List<DocResponse> fetchAllDocs();
    List<DocResponse> fetchDocsByCategoryName(String categoryName);

    DocResponse fetchDocById(Long docId);
    DocResponse createNewDocument(DocRequest docRequest);
    DocResponse modifiedExistDocumentById( Long docId, DocRequest docRequest);
    Void removeExistingDocumentById(Long docId);

}
