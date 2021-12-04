package com.auction.web.contoller;

import com.auction.exception.*;
import com.auction.web.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(AuctionRuntimeException.class)
  public ResponseEntity<ErrorDto> handleRuntimeException(AuctionRuntimeException e) {
    return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<List<ErrorDto>> handleConstraintViolationException(ConstraintViolationException e) {
    return ResponseEntity.badRequest().body(e.getConstraintViolations()
                                                    .stream()
                                                    .map(error -> new ErrorDto(error.getMessage()))
                                                    .collect(Collectors.toList()));
  }
}