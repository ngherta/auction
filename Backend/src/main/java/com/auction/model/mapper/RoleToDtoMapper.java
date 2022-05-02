package com.auction.model.mapper;

import com.auction.model.Role;
import com.auction.web.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
class RoleToDtoMapper implements Mapper<Role, RoleDto> {

  @Override
  public RoleDto map(Role entity) {
    RoleDto roleDto = new RoleDto();
    roleDto.setName(entity.getUserRole().getAuthority());
    return roleDto;
  }
}
