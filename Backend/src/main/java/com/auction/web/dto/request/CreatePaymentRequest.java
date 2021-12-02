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

    @JsonCreator
    public CreatePaymentRequest(@JsonProperty String cardNumber,
                                @JsonProperty LocalDateTime expirationDate,
                                @JsonProperty String cvv,
                                @JsonProperty String name,
                                @JsonProperty Long userId) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.name = name;
        this.userId = userId;
    }
}
