package com.auction.web.contoller;

import com.auction.exception.*;
import com.auction.web.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler
  public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("User doesn't exist!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleAuctionNotFoundException(AuctionEventNotFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("Auction event doesn't exist!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleStartPriceException(StartPriceNullException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("Start price of auction event can't be NULL!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleTokenConfirmationNotFoundException(TokenConfirmationNotFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("Token confirmation doesn't exist!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleUserAlreadyEnabledException(UserAlreadyEnabledException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("User already enabled!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleUserDoesntResetPasswordException(UserDoesntResetPasswordException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("User doesn't reset password!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleSameCredentialsException(SameCredentialsException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("User with the same credentials already register", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleExpiredJwtCustomException(ExpiredJwtCustomException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("JWT token is expired!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleIllegalArgumentCustomException(IllegalArgumentCustomException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("JWT claims string is empty!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleSignatureCustomException(SignatureCustomException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("Invalid JWT signature!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleMalformedJwtCustomException(MalformedJwtCustomException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("Invalid JWT token!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleUnsupportedJwtCustomException(UnsupportedJwtCustomException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("JWT token is unsupported!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleUserRoleNotFoundException(UserRoleNotFoundException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("User role doesn't exist!", e.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<?> handleUserDoesntHavePermissionException(UserDoesntHavePermissionException e) {
    return ResponseEntity.badRequest().body(new ErrorDto("User doesn't have access!", e.getMessage()));
  }
}
