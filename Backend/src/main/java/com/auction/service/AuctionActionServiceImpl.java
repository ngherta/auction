package com.auction.service;

import com.auction.config.jwt.JwtUtils;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionActionService;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.BetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class AuctionActionServiceImpl implements AuctionActionService {

  private final AuctionActionRepository auctionActionRepository;
  private final Mapper<AuctionAction, AuctionActionDto> auctionActionDtoMapper;
  private final AuctionEventRepository auctionEventRepository;
  private final AuctionEventService auctionEventService;
  private final Mapper<AuctionAction, AuctionActionDto> auctionActionToDtoMapper;
  private final UserRepository userRepository;
  private final UserService userService;


  @Override
  @Transactional
  public AuctionActionDto bet(Double bet, Long auctionId, Long userId) {
    User user = userService.findById(userId);

    AuctionEvent auctionEvent = auctionEventService.findById(auctionId);
    log.info("UserId : " + user.getId(), "; AuctionEvent " + auctionId);

    AuctionAction auctionAction = AuctionAction.builder()
        .auctionEvent(auctionEvent)
        .user(user)
        .bet(bet)
        .build();

    return auctionActionToDtoMapper.map(auctionActionRepository.save(auctionAction));
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuctionActionDto> getAllByAuctionId(Long auctionId) {
    AuctionEvent auctionEvent = auctionEventRepository.findById(auctionId)
            .orElseThrow(() -> new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "] doesn't exist."));

    List<AuctionAction> list = auctionActionRepository.findByAuctionEvent(auctionEvent);
    return auctionActionToDtoMapper.mapList(list);
  }

}
