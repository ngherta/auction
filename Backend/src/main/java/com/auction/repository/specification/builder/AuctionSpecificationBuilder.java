package com.auction.repository.specification.builder;

import com.auction.model.AuctionEvent;
import com.auction.repository.specification.AuctionEventSpecification;
import com.auction.repository.specification.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AuctionSpecificationBuilder {
  private final List<SearchCriteria> params;

  public AuctionSpecificationBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public AuctionSpecificationBuilder with(String key, String operation, Object value, boolean isOrPredicate) {
    params.add(new SearchCriteria(key, operation, value, isOrPredicate));
    return this;
  }

  public Specification<AuctionEvent> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification> specs = params.stream()
            .map(AuctionEventSpecification::new)
            .collect(Collectors.toList());

    Specification result = specs.get(0);

    for (int i = 1; i < params.size(); i++) {
      result = params.get(i)
              .isOrPredicate()
              ? Specification.where(result).or(specs.get(i))
              : Specification.where(result).and(specs.get(i));
    }
    return result;
  }
}
