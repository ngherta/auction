package com.auction.validator;

import com.auction.web.dto.request.SignupRequest;

public interface UserValidator {
  void validate(SignupRequest request);
}
