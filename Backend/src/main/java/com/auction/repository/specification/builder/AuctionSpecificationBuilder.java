package com.auction.repository.specification.builder;

import com.auction.model.AuctionEvent;
import com.auction.repository.specification.AuctionEventSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AuctionSpecificationBuilder {
  private final List<AuctionEventSpecification> params;

  public AuctionSpecificationBuilder() {
    params = new ArrayList<>();
  }

  public AuctionSpecificationBuilder with(Specification<AuctionEvent> specification, boolean isOrPredicate) {
    params.add(new AuctionEventSpecification(specification, isOrPredicate));
    return this;
  }

  public Specification<AuctionEvent> build() {
    if (params.isEmpty()) {
      return null;
    }

    Specification result = params.get(0).getSpecification();

    for (int i = 1; i < params.size(); i++) {
      result = params.get(i)
              .isOrPredicate()
              ? Specification.where(result).or(params.get(i).getSpecification())
              : Specification.where(result).and(params.get(i).getSpecification());
    }
    return result;
  }
}
