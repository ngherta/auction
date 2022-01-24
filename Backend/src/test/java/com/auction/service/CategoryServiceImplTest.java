package com.auction.service;

import com.auction.model.Category;
import com.auction.model.SubCategory;
import com.auction.model.enums.CategoryType;
import com.auction.model.fixture.CategoryFixture;
import com.auction.model.fixture.SubCategoriesFixture;
import com.auction.model.mapper.Mapper;
import com.auction.repository.CategoryRepository;
import com.auction.repository.SubCategoryRepository;
import com.auction.web.dto.CategoryDto;
import com.auction.web.dto.request.CreateCategoryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  private SubCategoryRepository subCategoryRepository;
  @Mock
  private Mapper<Category, CategoryDto> categoryDtoMapper;
  @Mock
  private Mapper<SubCategory, CategoryDto> subCategoryDtoMapper;

  private CategoryServiceImpl categoryService;

  private List<Category> listOfCategories = new ArrayList<>();
  private List<SubCategory> listOfSubCategories = new ArrayList<>();


  @BeforeEach
  public void setUp() {
    categoryService = new CategoryServiceImpl(categoryRepository,
                                              subCategoryRepository,
                                              categoryDtoMapper,
                                              subCategoryDtoMapper);

    listOfCategories = List.of(CategoryFixture.category());

    listOfSubCategories = SubCategoriesFixture.listOfCategories();
  }

  @Test
  void getCategoriesForCreateAuction_whenInvoked_returnListOfCategories() {
    when(categoryRepository.findAll()).thenReturn(listOfCategories);
    when(categoryService.getSubCategory(any(Category.class))).thenReturn(listOfSubCategories);

    assertThat(categoryService.getCategoriesForCreateAuction()).isNotEmpty();
  }

  @Test
  void getCategoriesForCreateAuction_whenSubCategoriesNotFoundForCategory_returnListOfCategories() {
    when(categoryRepository.findAll()).thenReturn(listOfCategories);
    when(categoryService.getSubCategory(any(Category.class))).thenReturn(null);

    assertThat(categoryService.getCategoriesForCreateAuction()).isNotEmpty();
  }

  @Test
  void createCategory_whenCategoryNameAlreadyExist_thrownCategoryAlreadyExistException() {
    String name = "ELECTRONIC";
    when(categoryService.categoryExistsByName(any(String.class))).thenReturn(true);

    CreateCategoryRequest request = new CreateCategoryRequest();
    request.setName(name);
    request.setType(CategoryType.CATEGORY);
    Exception exception = assertThrows(RuntimeException.class, () -> {
      categoryService.createCategory(request);
    });

    String expectedMessage = "Category with name " + name + " already exist!";
    String actualMessage = exception.getMessage();

    assertThat(actualMessage.contains(expectedMessage)).isTrue();
  }

  @Test
  void createSubCategory_whenSubCategoryNameAlreadyExist_thrownCategoryAlreadyExistException() {
    String name = "ELECTRONIC";
    when(categoryRepository.findById(any(Long.class))).thenReturn(java.util.Optional.ofNullable(listOfCategories.get(0)));
    when(categoryService.subCategoryExistsByNameAndCategory(any(String.class), any(Category.class))).thenReturn(true);

    CreateCategoryRequest request = new CreateCategoryRequest();
    request.setName(name);
    request.setMainCategoryId(listOfCategories.get(0).getId());
    request.setType(CategoryType.SUB_CATEGORY);
    Exception exception = assertThrows(RuntimeException.class, () -> {
      categoryService.createSubCategory(request);
    });

    String expectedMessage = "SubCategory with name [" + name + "] already exist!";
    String actualMessage = exception.getMessage();

    assertThat(actualMessage.contains(expectedMessage)).isTrue();
  }
}
