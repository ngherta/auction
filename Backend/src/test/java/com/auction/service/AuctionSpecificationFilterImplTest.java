package com.auction.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuctionSpecificationFilterImplTest {

  private AuctionSpecificationFilterImpl auctionSpecificationFilter;
  private String filter;

  @BeforeEach
  public void setUp() {
    filter = "title:xd,categories::1234";
  }
}
