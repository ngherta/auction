package com.auction.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotNull
    private String newPassword;
    @Email
    private String email;
}
