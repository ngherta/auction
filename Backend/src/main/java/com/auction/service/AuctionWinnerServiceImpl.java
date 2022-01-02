package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.service.interfaces.AuctionWinnerService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionWinnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionWinnerServiceImpl implements AuctionWinnerService {
  private final AuctionWinnerRepository auctionWinnerRepository;
  private final UserService userService;
  private final Mapper<AuctionWinner, AuctionWinnerDto> auctionWinnerDtoMapper;

  @Override
  @Transactional(readOnly = true)
  public List<AuctionWinnerDto> getAllAuctionWinnerForUser(Long userId) {
    User user = userService.findById(userId);
    return getAllAuctionWinnerForUser(user);
  }

  @Override
  @Transactional
  public void createForFinishPrice(AuctionEvent auctionEvent, User user) {
    AuctionWinner auctionWinner = AuctionWinner.builder()
            .user(user)
            .auctionEvent(auctionEvent)
            .price(auctionEvent.getFinishPrice())
            .build();

    auctionWinnerRepository.save(auctionWinner);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuctionWinnerDto> getAllAuctionWinnerForUser(User user) {
    List<AuctionWinner> auctionWinnerList = auctionWinnerRepository.findByUser(user);
    return auctionWinnerDtoMapper.mapList(auctionWinnerList);
  }
}
