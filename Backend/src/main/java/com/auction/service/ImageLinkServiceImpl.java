package com.auction.service;

import com.auction.exception.ImageLinkNotFoundException;
import com.auction.model.ImageLink;
import com.auction.model.enums.ImageLinkType;
import com.auction.model.mapper.Mapper;
import com.auction.repository.ImageLinkRepository;
import com.auction.service.interfaces.ImageLinkService;
import com.auction.web.dto.ImageLinkDto;
import com.auction.web.dto.request.ImageLinkCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class ImageLinkServiceImpl implements ImageLinkService {

  @Value("${spring.client.url}")
  private String[] clientUrl;

  private final ImageLinkRepository imageLinkRepository;
  private final Mapper<ImageLink, ImageLinkDto> imageLinkDtoMapper;

  @Override
  @Transactional
  public ImageLinkDto save(final ImageLinkCreateRequest request) {
    ImageLink imageLink = ImageLink.builder()
            .type(request.getType())
            .sequence(request.getSequence())
            .imageLink(request.getImageLink())
            .build();

    setUrlAndType(imageLink, request.getUrl());

    return imageLinkDtoMapper
            .map(imageLinkRepository
                         .save(imageLink));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ImageLinkDto> findAllBy(final ImageLinkType type) {
    return imageLinkDtoMapper
            .mapList(imageLinkRepository
                             .findAllByType(type));
  }

  @Override
  @Transactional
  public void delete(ImageLink imageLink) {
    imageLinkRepository.delete(imageLink);
  }

  @Override
  @Transactional
  public void deleteById(final Long id) {
    ImageLink imageLink = imageLinkRepository
            .findById(id)
            .orElseThrow(
                    () -> new ImageLinkNotFoundException("ImageLink[" + id + "] not found!"));
    delete(imageLink);
  }

  private ImageLink setUrlAndType(ImageLink imageLink, String url) {
    //TODO
//    String client = this.clientUrl
//            .replace("http://", "")
//            .replace("https://", "");
//
//    if (url.contains(client)) {
//      url =  url.replaceAll(this.clientUrl, "");
//    }
    imageLink.setInternalLink(true);
    imageLink.setUrl(url);

    return imageLink;
  }
}
