package com.auction.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
  String title;
  String errorMessage;

  public static ErrorDto from(String errorMessage,
                              String title) {
    ErrorDto result = new ErrorDto();
    result.setErrorMessage(errorMessage);
    result.setTitle(title);
    return result;
  }
}
