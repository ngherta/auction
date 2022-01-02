package com.auction.service.interfaces;

import com.auction.model.Category;
import com.auction.model.SubCategory;
import com.auction.web.dto.CategoryDto;
import com.auction.web.dto.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
  List<SubCategory> getSubCategory(Category category);

  List<CategoryDto> getCategoriesForCreateAuction();

  List<CategoryDto> create(List<CreateCategoryRequest> requests);

  CategoryDto create(CreateCategoryRequest request);

  CategoryDto createSubCategory(CreateCategoryRequest request);

  CategoryDto createCategory(CreateCategoryRequest request);

  boolean categoryExistsByName(String name);

  boolean subCategoryExistsByNameAndCategory(String name, Category category);
}
