package com.auction.model.mapper;

import com.auction.model.ImageLink;
import com.auction.web.dto.ImageLinkDto;
import org.springframework.stereotype.Component;

@Component
public class ImageLinkToDtoMapper implements Mapper<ImageLink, ImageLinkDto> {
  @Override
  public ImageLinkDto map(ImageLink entity) {
    return ImageLinkDto.builder()
            .id(entity.getId())
            .imageLink(entity.getImageLink())
            .type(entity.getType())
            .url(entity.getUrl())
            .sequence(entity.getSequence())
            .isInternalLink(entity.getInternalLink())
            .build();
  }
}
