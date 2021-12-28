package com.auction.model.mapper;

import com.auction.model.Category;
import com.auction.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryToDtoMapper implements Mapper<Category, CategoryDto>{
  @Override
  public CategoryDto map(Category entity) {
    return CategoryDto.builder()
            .id(entity.getId())
            .category(entity.getCategory())
            .type("CATEGORY")
            .build();
  }
}
