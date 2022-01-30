package com.auction.web.dto.request;

import com.auction.model.enums.ImageLinkType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ImageLinkCreateRequest {
  @NotBlank
  private String url;
  @NotBlank
  private String imageLink;
  private ImageLinkType type;
}
