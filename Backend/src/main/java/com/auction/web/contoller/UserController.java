package com.auction.web.contoller;

import com.auction.service.interfaces.ResetPasswordService;
import com.auction.service.interfaces.TokenConfirmationService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.request.ChangePasswordRequest;
import com.auction.web.dto.request.DeleteUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final ResetPasswordService resetPasswordService;
    private final UserService userService;
    private final TokenConfirmationService tokenConfirmationService;

    @PostMapping("/reset/password")
    public ResponseEntity resetPasswordByToken()
            throws MessagingException, UnsupportedEncodingException {
        String token= ":s";
        resetPasswordService.resetPasswordByToken(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset/password/confirm")
    public ResponseEntity resetPasswordConfirm(@RequestParam("code") String code) {
        resetPasswordService.verify(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/password")
    public ResponseEntity setNewPasswordAfterReset(@RequestBody ChangePasswordRequest request) {
        resetPasswordService.changePasswordAfterReset(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    //only for ADMIN
    @PostMapping("/disable/{userId}")
    public ResponseEntity disable(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.disable(userId));
    }

    @PostMapping("/enable/{userId}")
    public ResponseEntity enable(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.enable(userId));
    }


    @PostMapping("/verify")
    public ResponseEntity verifyUser(@RequestParam("code") String code) {
        tokenConfirmationService.confirm(code);
        return ResponseEntity.ok("Email confirmed!");
    }

    @GetMapping()
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest request) {
        userService.deleteUserById(request);

        return ResponseEntity.ok().build();
    }
}
