package com.auction.web.contoller;

import com.auction.service.interfaces.ResetPasswordService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.ChangePasswordRequest;
import com.auction.web.dto.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.UnsupportedEncodingException;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  private final ResetPasswordService resetPasswordService;
  private final UserService userService;

  @PostMapping("/reset/password/{email}")
  public ResponseEntity<Void> resetPasswordByEmail(@Valid
                                                   @PathVariable
                                                   @Email String email)
          throws MessagingException, UnsupportedEncodingException {
    resetPasswordService.resetPasswordByEmail(email);
    return ResponseEntity.ok().build();
  }

  @PutMapping
  public ResponseEntity<UserDto> update(@Valid @RequestBody UserUpdateRequest request) {
    return ResponseEntity.ok(userService.update(request));
  }

  @PostMapping("/update/password/{code}")
  public ResponseEntity<Void> disableAndSetNewPassword(@Valid @RequestBody ChangePasswordRequest request,
                                                       @PathVariable String code) {
    resetPasswordService.changePasswordAfterReset(request, code);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/disable/code/{code}")
  public ResponseEntity<Void> disableAndVerify(@PathVariable String code) {
    resetPasswordService.verifyAndDisableUser(code);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/disable/{userId}")
  public ResponseEntity<UserDto> disable(@PathVariable("userId") Long userId) {
    return ResponseEntity.ok(userService.disable(userId));
  }

  @PostMapping("/enable/{userId}")
  public ResponseEntity<UserDto> enable(@PathVariable("userId") Long userId) {
    return ResponseEntity.ok(userService.enable(userId));
  }

  @GetMapping
  public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok().body(userService.get(page, perPage));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.getById(userId));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUserById(userId);
    return ResponseEntity.ok().build();
  }
}
