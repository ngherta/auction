package com.auction.dto;

import com.auction.web.model.User;
import com.auction.web.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole userRole;

    public static UserDto from(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .password(user.getPassword())
                .userRole(user.getRole())
                .build();
        return userDto;
    }
}
