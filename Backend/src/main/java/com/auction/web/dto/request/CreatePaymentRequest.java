package com.auction.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreatePaymentRequest {

    @NotNull
    private String cardNumber;
    @NotNull
    private LocalDateTime expirationDate;
    @Size(min = 3, max = 3)
    private String cvv;
    private String name;
    private Long userId;
}
