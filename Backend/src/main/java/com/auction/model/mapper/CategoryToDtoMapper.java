package com.auction.model.mapper;

import com.auction.model.Category;
import com.auction.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class CategoryToDtoMapper implements Mapper<Category, CategoryDto>{
  @Override
  public CategoryDto map(Category entity) {
    return CategoryDto.builder()
            .id(entity.getId())
            .categoryName(entity.getCategory())
            .type("CATEGORY")
            .build();
  }
}
