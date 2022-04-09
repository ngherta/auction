package com.auction.model.mapper;

import com.auction.model.User;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserToDtoMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto map(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setEnabled(entity.isEnabled());
        userDto.setHasDefaultAddress(entity.hasDefaultAddress());
        userDto.setMoneyBalance(entity.getMoneyBalance());
        userDto.setNeedTutorial(entity.getNeedTutorial());
        if (entity.getBirthday() != null) {
            userDto.setBirthday(entity.getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yy")));
        }
        userDto.setUserRole(entity
                                    .getUserRoles()
                                    .stream()
                                    .map(e -> e.getUserRole()
                                            .getAuthority())
                                    .collect(Collectors.toSet()));

        return userDto;
    }
}
