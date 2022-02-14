package com.auction.service;

import com.auction.service.interfaces.ImageResizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class used for resize image from Cloudinary
 */
@Service
@RequiredArgsConstructor
public class ImageResizeServiceImpl implements ImageResizeService {

  private static final String TYPE = "c_scale";

  @Override
  public String resize(String image, int width) {
    String replaceStr = "/image/upload/";

    StringBuilder to = new StringBuilder();
    to.append("/image/upload/");
    to.append(TYPE);
    to.append(",w_");
    to.append(width);
    to.append("/");
    return image.replace(replaceStr, to);
  }
}
