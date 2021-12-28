package com.auction.service.interfaces;

import com.auction.model.Category;
import com.auction.model.SubCategory;
import com.auction.web.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
  List<SubCategory> getSubCategory(Category category);

  List<CategoryDto> getCategoriesForCreateAuction();
}
