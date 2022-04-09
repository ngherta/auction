package com.auction.web.contoller;

import com.auction.service.interfaces.CategoryService;
import com.auction.web.dto.request.CreateCategoryRequest;
import com.auction.web.dto.response.CategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin("http://34.140.181.128:8082")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoriesResponse>> getCategories() {
    return ResponseEntity.ok(categoryService.getCategoriesForCreateAuction());
  }

  @PostMapping
  public ResponseEntity<List<CategoriesResponse>> createNewCategory(@RequestBody List<CreateCategoryRequest> requests) {
    categoryService.create(requests);
    return ResponseEntity.ok(categoryService.getCategoriesForCreateAuction());
  }

}
