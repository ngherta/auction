package com.auction.web.contoller;

import com.auction.exception.*;
import com.auction.web.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackageClasses = {
    AuctionActionController.class,
    AuctionEventController.class,
    AuthController.class,
    CategoryController.class,
    ImageLinkController.class,
    NotificationWebsocketController.class,
    PaymentController.class,
    SearchWebSocketController.class,
    SettingsController.class,
    StatisticController.class,
    TokenController.class,
    UserController.class,
    ComplaintController.class,
    WebSocketController.class,
    IoTController.class
})
@Slf4j
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorDto>> handleNotValidException(MethodArgumentNotValidException e) {
    return ResponseEntity.badRequest().body(e.getFieldErrors()
                                                .stream()
                                                .map(error -> new ErrorDto(error.getDefaultMessage()))
                                                .collect(Collectors.toList()));
  }
}