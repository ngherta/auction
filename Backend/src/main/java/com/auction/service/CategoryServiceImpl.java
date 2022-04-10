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
import com.auction.web.dto.response.CategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final Mapper<Category, CategoryDto> categoryDtoMapper;
  private final Mapper<SubCategory, CategoryDto> subCategoryDtoMapper;

  @Override
  @Transactional(readOnly = true)
  public List<CategoriesResponse> getCategoriesForCreateAuction() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoriesResponse> responseList = new ArrayList<>();
    for (Category category : categories) {
      responseList.add(new CategoriesResponse(categoryDtoMapper.map(category),
                                              subCategoryDtoMapper.mapList(getSubCategory(category))));
    }
    return responseList;
  }

  @Override
  @Transactional
  public List<CategoryDto> create(List<CreateCategoryRequest> requests) {
    List<CategoryDto> list = new ArrayList<>();
    requests.forEach(e -> list.add(create(e)));
    return list;
  }

  @Override
  @Transactional
  public void deleteSubCategory(Long id) {
    SubCategory subCategory = subCategoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFound("SubCategory[" + id + "] not found!"));

    subCategoryRepository.delete(subCategory);
  }

  @Override
  @Transactional
  public void deleteCategory(Long id) {
    Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFound("Category[" + id + "] not found!"));

    categoryRepository.delete(category);
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubCategory> getSubCategoriesByIds(List<Long> subCategoryIds) {
    List<SubCategory> subCategories = subCategoryRepository.findByIdIn(subCategoryIds);
    if (subCategories.size() != subCategoryIds.size()) {
      List<Long> notFoundSubCategories = subCategoryIds.stream()
              .filter(id -> subCategories.stream()
                      .anyMatch(sc -> Objects.equals(sc.getId(), id)))
              .collect(Collectors.toList());
      throw new CategoryNotFound(
              "Sub categories with ids" + notFoundSubCategories.toArray() + " not found!");
    }
    return subCategories;
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
