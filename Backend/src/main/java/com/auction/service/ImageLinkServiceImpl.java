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
public class ImageLinkServiceImpl implements ImageLinkService {

  @Value("${spring.client.url}")
  private String clientUrl;

  private final ImageLinkRepository imageLinkRepository;
  private final Mapper<ImageLink, ImageLinkDto> imageLinkDtoMapper;

  @Override
  @Transactional
  public ImageLinkDto save(final ImageLinkCreateRequest request) {
    ImageLink imageLink = ImageLink.builder()
            .imageLink(request.getImageLink())
            .type(request.getType())
            .url(prepareUrl(request.getUrl()))
            .sequence(request.getSequence())
            .build();

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

  private String prepareUrl(final String url) {
    String clientUrl = this.clientUrl
            .replace("http://", "")
            .replace("https://", "");

    if (url.contains(clientUrl)) {
      return url.replaceAll(this.clientUrl, "");
    }
    return url;
  }
}
