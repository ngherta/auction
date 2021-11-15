package com.auction.service.interfaces;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;
import com.auction.dto.request.AuctionFinishByFinishPriceRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.web.model.AuctionEvent;
import com.auction.web.model.AuctionWinner;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AuctionEventService {
  AuctionEventDto save(AuctionEventRequest request);

  void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException;

  void finishByFinishPrice(AuctionFinishByFinishPriceRequest request) throws AuctionEventNotFoundException, UserNotFoundException;

  List<AuctionEventDto> getAllSortByRating();

  List<AuctionEventDto> getAll();

  void delete(AuctionEvent auctionEvent);

  AuctionEventDto update(AuctionEventRequest request, Long auctionId) throws AuctionEventNotFoundException;

  void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

  AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent);

  void search(String message);
}
