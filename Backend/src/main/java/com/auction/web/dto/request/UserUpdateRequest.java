package com.auction.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateRequest {
  @NotNull
  private Long id;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  private Boolean enabled;

  private LocalDate birthday;
}
