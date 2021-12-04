package com.auction.service.interfaces;

import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import com.auction.web.dto.request.AuctionFinishByFinishPriceRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.StartPriceNullException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AuctionEventService {
  AuctionEventDto save(AuctionEventRequest request);

  void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException;

  void finishByFinishPrice(AuctionFinishByFinishPriceRequest request);

  List<AuctionEventDto> getAllSortByRating();

  Page<AuctionEventDto> get(int page, int perPage);

  AuctionEventDto blockAuctionEventById(Long auctionId);

  void delete(AuctionEvent auctionEvent);

  void deleteById(Long auctionId);

  AuctionEventDto update(AuctionEventRequest request, Long auctionId);

  void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

  AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent);

  void search(String message);

  void changeStatusToStart(List<AuctionEvent> list);

  public AuctionEvent findById(Long id);
}
