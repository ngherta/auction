package com.auction.web.contoller;

import com.auction.dto.ErrorDto;
import com.auction.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<?> handleRepeatedVoteException(UserNotFoundException e) {
    return ResponseEntity.badRequest().body(ErrorDto.from("User doesn't exist!", e.getMessage()));
  }
}
