package com.auction.web.dto.response.statistic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommissionPerMouth {
    private Integer index;
    private String date;
    private String month;
    private Double amount;
}
