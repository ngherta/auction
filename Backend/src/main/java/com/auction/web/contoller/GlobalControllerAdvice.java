package com.auction.web.contoller;

import com.auction.web.dto.ErrorDto;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.StartPriceNullException;
import com.auction.exception.TokenConfirmationNotFoundException;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.exception.UserDoesntResetPassword;
import com.auction.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.badRequest().body(ErrorDto.from("User doesn't exist!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleAuctionNotFoundException(AuctionEventNotFoundException e) {
    return ResponseEntity.badRequest().body(ErrorDto.from("Auction event doesn't exist!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleStartPriceException(StartPriceNullException e) {
    return ResponseEntity.badRequest().body(ErrorDto.from("Start price of auction event can't be NULL!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleTokenConfirmationNotFoundException(TokenConfirmationNotFoundException e) {
    return ResponseEntity.badRequest().body(ErrorDto.from("Token confirmation doesn't exist!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleUserAlreadyEnabledException(UserAlreadyEnabledException e) {
    return ResponseEntity.badRequest().body(ErrorDto.from("User already enabled!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleUserDoesntResetPasswordException(UserDoesntResetPassword e) {
    return ResponseEntity.badRequest().body(ErrorDto.from("User doesn't reset password!", e.getMessage()));
  }
}
