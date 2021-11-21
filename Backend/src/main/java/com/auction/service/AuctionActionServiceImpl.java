package com.auction.service;

import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.BetRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.repository.AuctionEventRepository;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionActionServiceImpl implements AuctionActionService {

  private final AuctionActionRepository auctionActionRepository;
  private final EntityManager entityManager;
  private final AuctionEventRepository auctionEventRepository;

  @Override
  @Transactional
  public List<AuctionAction> bet(BetRequest request) throws AuctionEventNotFoundException {
    AuctionAction auctionAction = new AuctionAction();
    auctionAction.setBet(request.getPrice());
    auctionAction.setDate(new Date());
    auctionAction.setUser(entityManager.getReference(User.class, request.getUserId()));
    auctionAction.setAuctionEvent(entityManager.getReference(AuctionEvent.class, request.getAuctionId()));
    auctionAction = auctionActionRepository.save(auctionAction);
    //Check transaction, if last auctionAction exists in list
    return getAllByAuctionId(auctionAction.getAuctionEvent().getId());
  }

  @Override
  public List<AuctionAction> getAllByAuctionId(Long auctionId) throws AuctionEventNotFoundException {
    AuctionEvent auctionEvent = auctionEventRepository.findById(auctionId)
            .orElseThrow(() -> new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "] doesn't exist."));

    List<AuctionAction> list = auctionActionRepository.findByAuctionEvent(auctionEvent);
    return list;
  }

}
