package com.auction.service.interfaces;

import com.auction.model.ImageLink;
import com.auction.model.enums.ImageLinkType;
import com.auction.web.dto.ImageLinkDto;
import com.auction.web.dto.request.ImageLinkCreateRequest;

import java.util.List;

public interface ImageLinkService {

  ImageLinkDto save(ImageLinkCreateRequest request);

  List<ImageLinkDto> findAllBy(ImageLinkType type);

  void delete(ImageLink imageLink);

  void deleteById(Long id);
}
