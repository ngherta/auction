package com.auction.service.interfaces;

import com.auction.web.dto.WithdrawMoneyDto;
import com.auction.web.dto.request.WithdrawMoneyRequest;

import java.util.List;

public interface WithdrawMoneyService {

  void create(WithdrawMoneyRequest request);

  List<WithdrawMoneyDto> findAll();

  List<WithdrawMoneyDto> findByUserId(Long userId);

  void approve(Long id);
}
