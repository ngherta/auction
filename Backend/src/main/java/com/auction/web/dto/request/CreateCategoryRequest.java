package com.auction.web.dto.request;

import com.auction.model.enums.CategoryType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCategoryRequest {
  @NotBlank
  private String name;
  @NotBlank
  private CategoryType type;
  private Long mainCategoryId;
}
