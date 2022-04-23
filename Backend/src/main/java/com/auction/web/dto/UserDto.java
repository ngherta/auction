package com.auction.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String firstName;
    private String lastName;
    private String birthday;
    private Set<String> userRole;
    private Boolean enabled;
    private Boolean hasDefaultAddress;
    private String country;
    private String city;
    private String address;
    private Double moneyBalance;
    private Boolean needTutorial;
}
