package com.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequest {
    private String newPassword;
    private String email;

    @JsonCreator
    public ChangePasswordRequest(@JsonProperty String newPassword,
                                 @JsonProperty String email) {
        this.newPassword = newPassword;
        this.email = email;
    }
}
