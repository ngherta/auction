package com.auction.service;

import com.auction.exception.AuctionRuntimeException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.User;
import com.auction.model.WithdrawMoney;
import com.auction.model.enums.WithdrawMoneyStatus;
import com.auction.model.mapper.Mapper;
import com.auction.repository.UserRepository;
import com.auction.repository.WithdrawMoneyRepository;
import com.auction.service.interfaces.WithdrawMoneyService;
import com.auction.web.dto.WithdrawMoneyDto;
import com.auction.web.dto.request.WithdrawMoneyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class WithdrawMoneyServiceImpl implements WithdrawMoneyService {

  private final WithdrawMoneyRepository withdrawMoneyRepository;
  private final UserRepository userRepository;
  private final Mapper<WithdrawMoney, WithdrawMoneyDto> withdrawMoneyDtoMapper;

  @Override
  @Transactional
  public void create(WithdrawMoneyRequest request) {
    User user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new UserNotFoundException("User[" + request.getUserId() + "] not found!"));

    validate(request.getAmount(), user);

    WithdrawMoney withdrawMoney = WithdrawMoney.builder()
        .user(user)
        .amount(request.getAmount())
        .genDate(LocalDateTime.now())
        .status(WithdrawMoneyStatus.CREATED)
        .name(request.getName())
        .card(request.getCard())
        .date(toDateFrom(request.getDate()))
        .build();

    withdrawMoneyRepository.save(withdrawMoney);
  }

  private void validate(Double amount, User user) {
    List<WithdrawMoney> allByUser = withdrawMoneyRepository.findAllByUser(user);
    Double totalAmount = 0D;
    for (WithdrawMoney item : allByUser) {
      totalAmount += item.getAmount();
    }

    if (user.getMoneyBalance() < amount || user.getMoneyBalance() < totalAmount) {
      throw new AuctionRuntimeException("You don't have enough money!");
    }
  }

  private LocalDate toDateFrom(String date) {
    int month = Integer.parseInt(date.substring(0,1));
    int year = Integer.parseInt(date.substring(3));
    return LocalDate.of(year, month, 1);
  }

  @Override
  @Transactional(readOnly = true)
  public List<WithdrawMoneyDto> findAll() {
    return withdrawMoneyDtoMapper.mapList(withdrawMoneyRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public List<WithdrawMoneyDto> findByUserId(Long userId) {
    return withdrawMoneyDtoMapper.mapList(withdrawMoneyRepository.findAllByUserId(userId));
  }

  @Override
  @Transactional
  public void approve(Long id) {
    WithdrawMoney request = withdrawMoneyRepository.findById(id)
        .orElseThrow(() -> new AuctionRuntimeException("Request[" + id + "] not found!"));

    if (request.getUser().getMoneyBalance() < request.getAmount()) {
      withdrawMoneyRepository.delete(request);
      return;
    }

    request.setStatus(WithdrawMoneyStatus.FINISHED);

    User user = request.getUser();
    Double newBalance = request.getUser().getMoneyBalance() - request.getAmount();
    user.setMoneyBalance(newBalance);
  }
}
