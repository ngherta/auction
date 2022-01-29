package com.auction.web.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
