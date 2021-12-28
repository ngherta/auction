package com.auction.service;

import com.auction.model.Category;
import com.auction.model.SubCategory;
import com.auction.model.mapper.Mapper;
import com.auction.repository.CategoryRepository;
import com.auction.repository.SubCategoryRepository;
import com.auction.service.interfaces.CategoryService;
import com.auction.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final Mapper<Category, CategoryDto> categoryDtoMapper;
  private final Mapper<SubCategory, CategoryDto> subCategoryDtoMapper;

  @Override
  @Transactional(readOnly = true)
  public List<CategoryDto> getCategoriesForCreateAuction() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoryDto> categoryDtos = new ArrayList<>();
    for (Category category : categories) {
      categoryDtos.add(categoryDtoMapper.map(category));
      categoryDtos.addAll(subCategoryDtoMapper
                                  .mapList(getSubCategory(category)));
    }
    return categoryDtos;
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubCategory> getSubCategory(Category category) {
    return subCategoryRepository.findByCategory(category);
  }
}
