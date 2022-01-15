package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.enums.AuctionStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AuctionSpecificationFilter {
  Specification<AuctionEvent> filterByCategories(List<Long> categories);

  Specification<AuctionEvent> createFilter(String filter);

  Specification<AuctionEvent> filterByTitle(String title);

  Specification<AuctionEvent> filterByStatus(List<AuctionStatus> list);
}
