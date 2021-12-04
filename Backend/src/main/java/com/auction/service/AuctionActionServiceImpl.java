package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionActionService;
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
  private final EntityManager entityManager;
  private final AuctionEventRepository auctionEventRepository;
  private final Mapper<AuctionAction, AuctionActionDto> auctionActionToDtoMapper;

  @Override
  @Transactional
  public List<AuctionActionDto> bet(BetRequest request) {
    AuctionAction auctionAction = AuctionAction.builder()
            .bet(request.getPrice())
            .auctionEvent(entityManager.getReference(AuctionEvent.class, request.getAuctionId()))
            .user(entityManager.getReference(User.class, request.getUserId()))
            .build();

    auctionAction = auctionActionRepository.save(auctionAction);
    //Check transaction, if last auctionAction exists in list
    return getAllByAuctionId(auctionAction.getAuctionEvent().getId());
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
