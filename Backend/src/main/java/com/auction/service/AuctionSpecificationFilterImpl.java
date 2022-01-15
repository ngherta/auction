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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuctionSpecificationFilterImpl implements AuctionSpecificationFilter {

  @Override
  public Specification<AuctionEvent> createFilter(String filter) {
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|::)(\\w+?),");
    Matcher matcher = pattern.matcher(filter + ",");

    AuctionSpecificationBuilder auctionSpecificationBuilder = new AuctionSpecificationBuilder();
    List<Long> categories = new ArrayList<>();
    List<String> statuses = new ArrayList<>();

    while (matcher.find()) {
      if (matcher.group(1).equalsIgnoreCase("categories")
              && matcher.group(2).equalsIgnoreCase("::")) {
        categories.add(Long.parseLong(matcher.group(3)));
      }
      else if (matcher.group(1).equalsIgnoreCase("title")
              && matcher.group(2).equalsIgnoreCase(":")) {
        auctionSpecificationBuilder.with(filterByTitle(matcher.group(3)), false);
      }
      else if (matcher.group(1).equalsIgnoreCase("status")
              && matcher.group(2).equalsIgnoreCase("::")) {
        statuses.add(matcher.group(3));
      }
    }

    if (!categories.isEmpty()) {
      auctionSpecificationBuilder.with(filterByCategories(categories), false);
    }

    if (!statuses.isEmpty()) {
      auctionSpecificationBuilder
              .with(filterByStatus(AuctionStatus.from(statuses)), false);
    }

    return auctionSpecificationBuilder.build();
  }

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
  public Specification<AuctionEvent> filterByCategories(List<Long> categories) {
    return (root, query, builder) -> {
      Join<AuctionEvent, SubCategory> join = root.join("categories");
      return builder.in(join.get("id")).value(categories);
    };
  }
}
