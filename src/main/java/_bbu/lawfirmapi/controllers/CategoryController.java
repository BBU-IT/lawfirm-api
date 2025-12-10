package _bbu.lawfirmapi.controllers;

import _bbu.lawfirmapi.models.DTO.category.request.CateRequest;
import _bbu.lawfirmapi.models.DTO.category.response.CateResponse;
import _bbu.lawfirmapi.models.DTO.doc.request.DocRequest;
import _bbu.lawfirmapi.models.DTO.doc.response.DocResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.ApiResponse;
import _bbu.lawfirmapi.models.DTO.shared.response.BaseResponse;
import _bbu.lawfirmapi.models.Entity.Category;
import _bbu.lawfirmapi.models.Entity.Document;
import _bbu.lawfirmapi.services.category.CategoryService;
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
@RequestMapping("/api/v1/categories")
public class CategoryController  extends BaseResponse {

    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories(){
        return responseEntity(true ,
                "Get all categories successfully",
                HttpStatus.OK,
                categoryService.fetchAllCategories());
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable @Valid @Positive Long categoryId){
        return responseEntity(true ,
                "Get category with id " + categoryId + " successfully",
                HttpStatus.ACCEPTED,
                categoryService.fetchCateById(categoryId));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<CateResponse>> insertNewCategory(@RequestBody CateRequest cateRequest){
        return responseEntity(true ,
                "Create new category successfully",
                HttpStatus.CREATED,
                categoryService.createNewCategory(cateRequest));
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CateResponse>> updateDocById(
            @PathVariable @Valid @Positive Long categoryId ,
            @RequestBody CateRequest cateRequest
    ) {
        return responseEntity(true ,
                "Update document successfully",
                HttpStatus.ACCEPTED,
                categoryService.modifiedExistCategoryById(categoryId , cateRequest));
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(@PathVariable @Valid @Positive Long categoryId){
        return responseEntity(true ,
                "Delete document "+ categoryService.fetchCateById(categoryId).getCategoryName()  + " successfully",
                HttpStatus.OK,
                categoryService.removeExistingCategoryById(categoryId));
    }
}
