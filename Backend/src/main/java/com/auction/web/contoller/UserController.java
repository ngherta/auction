package com.auction.web.contoller;

import com.auction.dto.request.ChangePasswordRequest;
import com.auction.dto.request.DeleteUserRequest;
import com.auction.exception.TokenConfirmationNotFoundException;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.exception.UserNotFoundException;
import com.auction.service.interfaces.ResetPasswordService;
import com.auction.service.interfaces.TokenConfirmationService;
import com.auction.service.interfaces.UserService;
import com.auction.web.model.TokenConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final ResetPasswordService resetPasswordService;
    private final UserService userService;
    private final TokenConfirmationService tokenConfirmationService;

    @PostMapping("/reset/password/email={email}")
    public ResponseEntity resetPasswordByEmail(@PathVariable String email) throws MessagingException, UnsupportedEncodingException {
        resetPasswordService.resetPasswordByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset/password/code={code}")
    public ResponseEntity resetPasswordConfirm(@PathVariable String code) {
        resetPasswordService.verify(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset/password")
    public ResponseEntity setNewPasswordAfterReset(@RequestBody ChangePasswordRequest request) {
        resetPasswordService.changePasswordAfterReset(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity verifyUser(@Param("code") String code) throws TokenConfirmationNotFoundException, UserAlreadyEnabledException {
        tokenConfirmationService.confirm(code);
        return ResponseEntity.ok("Email confirmed!");
    }

    @GetMapping()
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody DeleteUserRequest request) throws UserNotFoundException {
        userService.deleteUserById(request);

        return ResponseEntity.ok().build();
    }
}
