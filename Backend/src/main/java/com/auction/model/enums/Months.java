package com.auction.model.enums;

public enum Months {
  JANUARY("January", 1),
  FEBRUARY("February", 2),
  MARCH("March", 3),
  APRIL("April", 4),
  MAY("May", 5),
  JUNE("June", 6),
  JULY("July", 7),
  AUGUST("August", 8),
  SEPTEMBER("September", 9),
  OCTOBER("October", 10),
  NOVEMBER("November", 11),
  DECEMBER("December", 12);

  public final String label;
  public final Integer index;

  public String getLabel() {
    return label;
  }

  public Integer getIndex() {
    return index;
  }

  Months(String label,
         Integer index) {
    this.label = label;
    this.index = index;
  }
}
