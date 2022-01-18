//package com.auction.service;
//
//import com.auction.model.Category;
//import com.auction.model.SubCategory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class AuctionSpecificationFilterImplTest {
//
//  private AuctionSpecificationFilterImpl auctionSpecificationFilter;
//  private String filter;
//
//  @BeforeEach
//  public void setUp() {
//    filter = "title:xd,categories::1234";
//  }
//
//  @Test
//  void getCategoriesForCreateAuction_whenInvoked_returnListOfCategories() {
//    when(categoryRepository.findAll()).thenReturn(listOfCategories);
//    when(categoryService.getSubCategory(any(Category.class))).thenReturn(listOfSubCategories);
//
//    assertThat(categoryService.getCategoriesForCreateAuction()).isNotEmpty();
//  }
//}
