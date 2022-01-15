package com.auction.repository.specification;

import com.auction.model.AuctionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@AllArgsConstructor
public class AuctionEventSpecification {
  private Specification<AuctionEvent> specification;
  private boolean isOrPredicate;
}
