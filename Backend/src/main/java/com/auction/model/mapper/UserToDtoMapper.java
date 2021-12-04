package com.auction.model.mapper;

import com.auction.model.Role;
import com.auction.model.User;
import com.auction.web.dto.RoleDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToDtoMapper implements Mapper<User, UserDto> {

  private final Mapper<Role, RoleDto> roleToDtoMapper;

  @Override
  public UserDto map(User entity) {
      UserDto userDto = new UserDto();
      userDto.setId(entity.getId());
      userDto.setEmail(entity.getEmail());
      userDto.setFirstName(entity.getFirstName());
      userDto.setLastName(entity.getLastName());
      userDto.setUserRole(roleToDtoMapper.mapSet(entity.getUserRoles()));

      return userDto;
  }
}
