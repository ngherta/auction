package com.auction.web.contoller;

import com.auction.service.interfaces.CategoryService;
import com.auction.web.dto.CategoryDto;
import com.auction.web.dto.request.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getCategories() {
    return ResponseEntity.ok(categoryService.getCategoriesForCreateAuction());
  }

  @PostMapping
  public ResponseEntity<List<CategoryDto>> createNewCategory(@RequestBody List<CreateCategoryRequest> requests) {
    return ResponseEntity.ok(categoryService.create(requests));
  }

}
