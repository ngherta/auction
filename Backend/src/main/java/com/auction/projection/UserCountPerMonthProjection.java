package com.auction.projection;

public interface UserCountPerMonthProjection {
  String getDate();

  String getYear();

  String getMonth();

  Long getCount();

  Integer getIndex();
}
