package com.auction.model.enums;

public enum Categories {
  // Main:
  CLOTHING("Clothing"),
  ELECTRONIC("Electronic"),

  //Subcategories:
  T_SHIRT("T-shirt"),
  PHONE("Phone"),
  LAPTOP("Laptop");

  public final String label;

  private Categories(String label) {
    this.label = label;
  }
}
