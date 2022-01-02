package com.auction.service;

import com.auction.exception.CategoryAlreadyExistException;
import com.auction.exception.CategoryNotFound;
import com.auction.model.Category;
import com.auction.model.SubCategory;
import com.auction.model.enums.CategoryType;
import com.auction.model.mapper.Mapper;
import com.auction.repository.CategoryRepository;
import com.auction.repository.SubCategoryRepository;
import com.auction.service.interfaces.CategoryService;
import com.auction.web.dto.CategoryDto;
import com.auction.web.dto.request.CreateCategoryRequest;
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
  @Transactional
  public List<CategoryDto> create(List<CreateCategoryRequest> requests) {
    List<CategoryDto> list = new ArrayList<>();
    requests.stream().forEach(e -> list.add(create(e)));
    return list;
  }

  @Override
  @Transactional
  public CategoryDto create(CreateCategoryRequest request) {
    if (request.getType() == CategoryType.CATEGORY) {
      return createCategory(request);
    }
    else if (request.getType() == CategoryType.SUB_CATEGORY) {
      return createSubCategory(request);
    }
    return null;
  }

  @Transactional
  @Override
  public CategoryDto createCategory(CreateCategoryRequest request) {
    if (categoryExistsByName(request.getName())) {
      throw new CategoryAlreadyExistException("Category with name " + request.getName() + " already exist!");
    }

    Category category = Category.builder()
            .category(request.getName())
            .build();
    return categoryDtoMapper.map(categoryRepository.save(category));
  }

  @Transactional
  @Override
  public CategoryDto createSubCategory(CreateCategoryRequest request) {
    Category category = categoryRepository.findById(request.getMainCategoryId())
            .orElseThrow(() -> new CategoryNotFound("Category[" + request.getMainCategoryId() + "] not found!"));

    if (subCategoryExistsByNameAndCategory(request.getName(), category)) {
      throw new CategoryAlreadyExistException("SubCategory with name [" + request.getName() + "] already exist!");
    }

    SubCategory subCategory = SubCategory.builder()
            .subCategoryName(request.getName())
            .category(category)
            .build();

    return subCategoryDtoMapper.map(subCategoryRepository.save(subCategory));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean categoryExistsByName(String name) {
    return categoryRepository.existsByCategory(name);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean subCategoryExistsByNameAndCategory(String name, Category category) {
    return subCategoryRepository.existsByCategoryAndSubCategoryName(category, name);
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubCategory> getSubCategory(Category category) {
    return subCategoryRepository.findByCategory(category);
  }
}