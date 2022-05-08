package com.auction.model.mapper;

import com.auction.model.User;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class UserToDtoMapper implements Mapper<User, UserDto> {

  @Override
  public UserDto map(User e) {
    return UserDto.builder()
        .id(e.getId())
        .email(e.getEmail())
        .firstName(e.getFirstName())
        .lastName(e.getLastName())
        .fullName(e.getFirstName() + " " + e.getLastName())
        .enabled(e.isEnabled())
        .hasDefaultAddress(e.hasDefaultAddress())
        .moneyBalance(Double.parseDouble(new DecimalFormat("##.##")
                                             .format(e.getMoneyBalance())))
        .needTutorial(e.getNeedTutorial())
        .birthday(e.getBirthday() == null ?
                      null :
                      e.getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yy")))
        .userRole(e
                      .getUserRoles()
                      .stream()
                      .map(r -> r.getUserRole()
                          .getAuthority())
                      .collect(Collectors.toSet()))
        .build();
  }
}
