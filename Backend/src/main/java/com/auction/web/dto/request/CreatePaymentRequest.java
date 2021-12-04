package com.auction.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreatePaymentRequest {

    private String cardNumber;
    private LocalDateTime expirationDate;
    private String cvv;
    private String name;
    private Long userId;
}
