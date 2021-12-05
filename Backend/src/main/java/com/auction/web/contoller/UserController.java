package com.auction.web.contoller;

import com.auction.service.interfaces.ResetPasswordService;
import com.auction.service.interfaces.TokenConfirmationService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.ChangePasswordRequest;
import com.auction.web.dto.request.DeleteUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final ResetPasswordService resetPasswordService;
    private final UserService userService;
    private final TokenConfirmationService tokenConfirmationService;

    @PostMapping("/reset/password")
    public ResponseEntity<Void> resetPasswordByToken(@RequestHeader("Authorization") String token)
            throws MessagingException, UnsupportedEncodingException {
        resetPasswordService.resetPasswordByToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset/password/confirm")
    public ResponseEntity<Void> resetPasswordConfirm(@RequestParam("code") String code) {
        resetPasswordService.verify(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/password")
    public ResponseEntity<Void> setNewPasswordAfterReset(@RequestBody ChangePasswordRequest request) {
        resetPasswordService.changePasswordAfterReset(request.getEmail(), request.getNewPassword());
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
    public ResponseEntity<UserDto> getUser(Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequest request) {
        userService.deleteUserById(request);
        return ResponseEntity.ok().build();
    }
}
