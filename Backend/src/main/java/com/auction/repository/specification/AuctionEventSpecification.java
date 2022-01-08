package com.auction.repository.specification;

import com.auction.model.AuctionEvent;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class AuctionEventSpecification implements Specification<AuctionEvent> {

  private SearchCriteria criteria;

  @Override
  public Predicate toPredicate(Root<AuctionEvent> root,
                               CriteriaQuery<?> query,
                               CriteriaBuilder builder) {
    if (criteria.getOperation().equalsIgnoreCase(">")) {
      return builder.greaterThanOrEqualTo(
              root.get(criteria.getKey()), criteria.getValue().toString());
    }
    else if (criteria.getOperation().equalsIgnoreCase("<")) {
      return builder.lessThanOrEqualTo(
              root.get(criteria.getKey()), criteria.getValue().toString());
    }
    else if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return builder.like(
                root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
      }
      else {
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
      }
    }
    else if (criteria.getOperation().equalsIgnoreCase("::")
            && criteria.getKey().equalsIgnoreCase("categories")) {
      root.
      return root.join("categories").get("subCa").in(criteria.getValue());
    }
    return null;
  }
}
