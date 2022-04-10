package com.auction.model.fixture;

import com.auction.model.SubCategory;

import java.util.List;

public class SubCategoriesFixture {
  private static final String name = "LAPTOP";

  public static SubCategory category () {
    return SubCategory.builder()
            .category(CategoryFixture.category())
            .subCategoryName(name)
            .build();
  }

  public static List<SubCategory> listOfCategories() {
    return List.of(category());
  }
}
