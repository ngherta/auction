package com.auction.repository;

import com.auction.model.Category;
import com.auction.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
  List<SubCategory> findByCategory(Category category);

}
