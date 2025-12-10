package _bbu.lawfirmapi.services.doc.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.doc.request.DocRequest;
import _bbu.lawfirmapi.models.DTO.doc.response.DocResponse;
import _bbu.lawfirmapi.models.Entity.Category;
import _bbu.lawfirmapi.models.Entity.Document;
import _bbu.lawfirmapi.repositories.CategoryRepository;
import _bbu.lawfirmapi.repositories.DocumentRepository;
import _bbu.lawfirmapi.services.doc.DocService;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
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
                       doc.getCategory().getCategoryName()
               )
       ).toList();
        return listOfDocs;
    }
    public class DocumentSpecs {
        public static Specification<Document> categoryNameContains(String name) {
            return (root, query, cb) -> {
                Join<Document, Category> categoryJoin = root.join("category");
                return cb.like(cb.upper(categoryJoin.get("categoryName")), "%" + name.toUpperCase() + "%");
            };
        }
    }

    @Override
    public List<DocResponse> fetchDocsByCategoryName(String categoryName){
        List<DocResponse> docsByCateName = documentRepo.findAll(DocumentSpecs.categoryNameContains(categoryName.toUpperCase()))
                .stream()
                .map(
                        doc -> new DocResponse(
                                doc.getDocId(),
                                doc.getTitle(),
                                doc.getFileCover(),
                                doc.getFileUrl(),
                                doc.getCategory().getCategoryName()
                        )
                ).toList();

        System.out.println("mama " + docsByCateName);
        return docsByCateName;
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
