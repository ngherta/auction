package com.auction.service;

import com.auction.dto.AuctionActionDto;
import com.auction.dto.request.BetRequest;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.repository.AuctionActionRepository;
import com.auction.service.interfaces.AuctionActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionActionServiceImpl implements AuctionActionService {

  private final AuctionActionRepository auctionActionRepository;
  private final EntityManager entityManager;

  @Override
  @Transactional
  public List<AuctionActionDto> bet(BetRequest request) {
    AuctionAction auctionAction = new AuctionAction();
    auctionAction.setBet(request.getPrice());
    auctionAction.setDate(new Date());
    auctionAction.setUser(entityManager.getReference(User.class, request.getUserId()));
    auctionAction.setAuctionEvent(entityManager.getReference(AuctionEvent.class, request.getAuctionId()));
    auctionAction = auctionActionRepository.save(auctionAction);
    //Check transaction, if last auctionAction exists in list
    return getAllByAuction(auctionAction.getAuctionEvent());
  }

  @Override
  public List<AuctionActionDto> getAllByAuction(AuctionEvent auctionEvent) {
    List<AuctionAction> list = auctionActionRepository.findByAuctionEvent(auctionEvent);
    List<AuctionActionDto> listDto = AuctionActionDto.from(list);
    return listDto;
  }

}
