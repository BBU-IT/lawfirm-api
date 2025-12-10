package _bbu.lawfirmapi.services.category;

import _bbu.lawfirmapi.models.DTO.category.request.CateRequest;
import _bbu.lawfirmapi.models.DTO.category.response.CateResponse;
import _bbu.lawfirmapi.models.DTO.doc.request.DocRequest;
import _bbu.lawfirmapi.models.DTO.doc.response.DocResponse;
import _bbu.lawfirmapi.models.Entity.Category;
import _bbu.lawfirmapi.models.Entity.Document;

import java.util.List;

public interface CategoryService {
    List<Category> fetchAllCategories();
    Category fetchCateById(Long docId);
    CateResponse createNewCategory(CateRequest cateRequest);
    CateResponse modifiedExistCategoryById( Long cateId, CateRequest cateRequest);
    Void removeExistingCategoryById(Long cateId);
}
