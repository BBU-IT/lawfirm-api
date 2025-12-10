package _bbu.lawfirmapi.services.category.implement;

import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.category.request.CateRequest;
import _bbu.lawfirmapi.models.DTO.category.response.CateResponse;
import _bbu.lawfirmapi.models.Entity.Category;
import _bbu.lawfirmapi.repositories.CategoryRepository;
import _bbu.lawfirmapi.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    @Override
    public List<Category> fetchAllCategories() {
        if(categoryRepo.findAll().isEmpty()){
            throw  new NotFoundException("List of category not found");
        }
        return categoryRepo.findAll();
    }

    @Override
    public Category fetchCateById(Long cateId) {
        return categoryRepo.findById(cateId).orElseThrow(
                () -> new NotFoundException("Category with id " + cateId +  " not found.")
        );
    }

    @Override
    public CateResponse createNewCategory(CateRequest cateRequest) {

        Category newCategory = cateRequest.toEntity();
        newCategory.setCategoryName(cateRequest.getCategoryName().toUpperCase());
        newCategory.setCreatedAt(LocalDateTime.now());
        CateResponse saveNewCate = categoryRepo.save(newCategory).toResponse();
        return saveNewCate;
    }

    @Override
    public CateResponse modifiedExistCategoryById(Long cateId, CateRequest cateRequest) {
        Category currentCate = categoryRepo.findById(cateId).orElseThrow(
                () -> new NotFoundException("Category with id " + cateId +  " not found.")
        );
        currentCate.setCategoryName(cateRequest.getCategoryName().toUpperCase());
        currentCate.setUpdatedAt(LocalDateTime.now());
        CateResponse saveUpdateCate = categoryRepo.save(currentCate).toResponse();
        return saveUpdateCate;
    }

    @Override
    public Void removeExistingCategoryById(Long cateId) {
         categoryRepo.findById(cateId).orElseThrow(
                () -> new NotFoundException("Category with id " + cateId +  " not found.")
        );
        categoryRepo.deleteById(cateId);
        return null;
    }
}
