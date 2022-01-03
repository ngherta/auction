package com.auction.web.dto.response;

import com.auction.web.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoriesResponse {
  private CategoryDto mainCategory;
  private List<CategoryDto> listSubCategories;
}
