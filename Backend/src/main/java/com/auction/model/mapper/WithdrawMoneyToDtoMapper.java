package com.auction.model.mapper;

import com.auction.model.User;
import com.auction.model.WithdrawMoney;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.WithdrawMoneyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WithdrawMoneyToDtoMapper implements Mapper<WithdrawMoney, WithdrawMoneyDto>{

  private final Mapper<User, UserDto> userDtoMapper;

  @Override
  public WithdrawMoneyDto map(WithdrawMoney e) {
    return WithdrawMoneyDto.builder()
        .id(e.getId())
        .amount(e.getAmount())
        .genDate(e.getGenDate().toString())
        .status(e.getStatus())
        .user(userDtoMapper.map(e.getUser()))
        .date(e.getDate().toString())
        .name(e.getName())
        .card(e.getCard())
        .build();
  }
}
