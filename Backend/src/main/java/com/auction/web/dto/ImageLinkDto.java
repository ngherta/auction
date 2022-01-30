package com.auction.web.dto;

import com.auction.model.enums.ImageLinkType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageLinkDto {
  private String url;
  private String imageLink;
  private ImageLinkType type;
}
