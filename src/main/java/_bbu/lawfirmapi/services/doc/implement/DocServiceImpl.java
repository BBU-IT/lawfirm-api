package _bbu.lawfirmapi.services.doc.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.doc.request.DocRequest;
import _bbu.lawfirmapi.models.DTO.doc.response.DocResponse;
import _bbu.lawfirmapi.models.Entity.Category;
import _bbu.lawfirmapi.models.Entity.Document;
import _bbu.lawfirmapi.repositories.CategoryRepository;
import _bbu.lawfirmapi.repositories.DocumentRepository;
import _bbu.lawfirmapi.services.doc.DocService;
import _bbu.lawfirmapi.utils.MethodHelper;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocServiceImpl implements DocService  {
    private final DocumentRepository documentRepo;
    private final CategoryRepository categoryRepository;
    private final MethodHelper methodHelper;

    @Override
    public List<DocResponse> fetchAllDocs() {
        if(documentRepo.findAll().isEmpty()){
            throw  new NotFoundException("List document not found");
        }
       List<DocResponse> listOfDocs = documentRepo.findAll().stream().map(
               doc -> new DocResponse(
                       doc.getDocId(),
                       doc.getTitle(),
                       doc.getFileCover(),
                       doc.getFileUrl(),
                       doc.getCategory().getCategoryName(),
                       doc.getCreatedAt(),
                       doc.getUpdatedAt()
               )
       ).toList();
        return listOfDocs;
    }
    public class DocumentSpecs {

    }

    @Override
    public Page<DocResponse> fetchDocWithPagination(Pageable pageable , Integer requestPage){

        Page<DocResponse> docResponse = documentRepo.findAll(pageable).map(
                doc -> new DocResponse(
                        doc.getDocId(),
                        doc.getTitle(),
                        doc.getFileCover(),
                        doc.getFileUrl(),
                        doc.getCategory().getCategoryName(),
                        doc.getCreatedAt(),
                        doc.getUpdatedAt()
                )
        );

        methodHelper.isInvalidPage(docResponse.getTotalPages() , requestPage);
        if(docResponse.isEmpty()){
            throw  new NotFoundException("Document list not found.");
        }
        return  docResponse;
    }

    @Override
    public List<DocResponse> fetchDocsByCategoryName(String categoryName){
        List<DocResponse> docsByCateName = documentRepo.findAll(methodHelper.categoryNameContains(categoryName.toUpperCase()))
                .stream()
                .map(
                        doc -> new DocResponse(
                                doc.getDocId(),
                                doc.getTitle(),
                                doc.getFileCover(),
                                doc.getFileUrl(),
                                doc.getCategory().getCategoryName(),
                                doc.getCreatedAt(),
                                doc.getUpdatedAt()
                        )
                ).toList();


        return docsByCateName;
    }
    @Override
    public List<DocResponse> fetchDocByKeyword(String keyword , String categoryName){
        List<DocResponse> listDocs = documentRepo.searchDocs(keyword , categoryName);
        if(listDocs.isEmpty()){
            throw new NotFoundException("Document with category name "  + "categoryName " + categoryName + " and keyword " + keyword +" Not found.");
        }

        return listDocs;
    }

    @Override
    public DocResponse fetchDocById(Long docId){
        return documentRepo.findById(docId).orElseThrow(
                () -> new NotFoundException("Document with id " + docId +  " not found.")
        ).toResponse();
    }
    @Override
    public DocResponse createNewDocument(DocRequest docRequest) {
        Document newDoc = docRequest.toEntity();
        Category category = categoryRepository.findById(docRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        newDoc.setTitle(docRequest.getTitle());
        newDoc.setFileCover(docRequest.getFileCover());
        newDoc.setFileUrl(docRequest.getFileUrl());
        newDoc.setCategory(category);
        newDoc.setCreatedAt(LocalDateTime.now());
        DocResponse saveNewDoc = documentRepo.save(newDoc).toResponse();
        return saveNewDoc;
    }

    @Override
    public DocResponse modifiedExistDocumentById( Long docId, DocRequest docRequest) {
        Document currentDoc =  documentRepo.findById(docId).orElseThrow(
                () -> new NotFoundException("Document with id " + docId +  " not found.")
        );
        Category category = categoryRepository.findById(docRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));



        currentDoc.setTitle(docRequest.getTitle());
        currentDoc.setFileCover(docRequest.getFileCover());
        currentDoc.setFileUrl(docRequest.getFileUrl());
        currentDoc.setCategory(category);

        currentDoc.setUpdatedAt(LocalDateTime.now());
        DocResponse saveUpdateDoc = documentRepo.save(currentDoc).toResponse();
        return saveUpdateDoc;
    }

    @Override
    public Void removeExistingDocumentById(Long docId) {
        documentRepo.findById(docId).orElseThrow(
                () -> new NotFoundException("Document with id " + docId +  " not found.")
        );
        documentRepo.deleteById(docId);
        return null;
    }
}
