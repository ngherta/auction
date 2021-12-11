package com.auction.web.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String password;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;
}
