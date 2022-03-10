package com.auction.web.dto.request;

import lombok.Data;

@Data
public class AddDefaultAddressRequest {
  private Long userId;
  private String country;
  private String city;
  private String address;
}
