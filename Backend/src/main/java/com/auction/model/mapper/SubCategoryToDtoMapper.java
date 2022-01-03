package com.auction.model.mapper;

import com.auction.model.SubCategory;
import com.auction.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SubCategoryToDtoMapper implements Mapper<SubCategory, CategoryDto>{
  @Override
  public CategoryDto map(SubCategory entity) {
    return CategoryDto.builder()
            .id(entity.getId())
            .categoryName(entity.getSubCategoryName())
            .type("SUB_CATEGORY")
            .build();
  }
}
