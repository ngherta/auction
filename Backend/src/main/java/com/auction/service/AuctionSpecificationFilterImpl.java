package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.SubCategory;
import com.auction.model.enums.AuctionStatus;
import com.auction.repository.specification.builder.AuctionSpecificationBuilder;
import com.auction.service.interfaces.AuctionSpecificationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import java.util.List;

@Service
@RequiredArgsConstructor
class AuctionSpecificationFilterImpl implements AuctionSpecificationFilter {

  @Override
  public Specification<AuctionEvent> filterByTitle(String title) {
    return (root, query, builder) -> builder.like(
            builder.lower(root.get("title")),
            "%" + title.toLowerCase() + "%");
  }

  @Override
  public Specification<AuctionEvent> filterByStatus(List<AuctionStatus> list) {
    return ((root, query, builder) ->
            builder.in(root.get("statusType")).value(list));
  }

  @Override
  public Specification<AuctionEvent> createFilter(String title,
                                                  List<Long> categoriesIds,
                                                  List<AuctionStatus> statuses) {
    AuctionSpecificationBuilder auctionSpecificationBuilder = new AuctionSpecificationBuilder();
    if (!title.isBlank()) {
      auctionSpecificationBuilder.with(filterByTitle(title), false);
    }
    if (!categoriesIds.isEmpty()) {
      auctionSpecificationBuilder.with(filterByCategories(categoriesIds), false);
    }
    if (!statuses.isEmpty()) {
      auctionSpecificationBuilder.with(filterByStatus(statuses),false);
    }
    return auctionSpecificationBuilder.build();
  }

  @Override
  public Specification<AuctionEvent> filterByCategories(List<Long> categories) {
    return (root, query, builder) -> {
      Join<AuctionEvent, SubCategory> join = root.join("categories");
      return builder.in(join.get("id")).value(categories);
    };
  }
}
