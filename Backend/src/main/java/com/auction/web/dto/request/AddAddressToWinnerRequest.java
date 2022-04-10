package com.auction.web.dto.request;

import lombok.Data;

@Data
public class AddAddressToWinnerRequest {

  private Long auctionId;
  private String country;
  private String city;
  private String address;
  private Boolean saveAsDefault;
}
