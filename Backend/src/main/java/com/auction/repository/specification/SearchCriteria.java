package com.auction.repository.specification;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor

@Data
public class SearchCriteria {
  private String key;
  private String operation;
  private Object value;
  private boolean isOrPredicate;
}
