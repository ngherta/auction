package com.auction.projection;

public interface CommissionPerMouthProjection {
    String getDate();

    String getYear();

    String getMonth();

    Double getAmount();

    Integer getIndex();
}
