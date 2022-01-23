package com.auction.model.fixture;

import com.auction.model.Category;

public class CategoryFixture {
  private static final String name = "ELECTRONIC";

  public static Category category() {
    return Category.builder()
            .category(name)
            .build();
  }
}
