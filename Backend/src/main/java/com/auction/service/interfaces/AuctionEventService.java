package com.auction.service.interfaces;

import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import com.auction.web.dto.request.AuctionFinishByFinishPriceRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.StartPriceNullException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AuctionEventService {
  AuctionEvent save(AuctionEventRequest request) throws StartPriceNullException;

  void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException;

  void finishByFinishPrice(AuctionFinishByFinishPriceRequest request) throws AuctionEventNotFoundException, UserNotFoundException;

  List<AuctionEvent> getAllSortByRating();

  List<AuctionEvent> getAll();

  AuctionEvent blockAuctionEventById(Long auctionId) throws AuctionEventNotFoundException;

  void delete(AuctionEvent auctionEvent);

  void deleteById(Long auctionId) throws AuctionEventNotFoundException;

  AuctionEvent update(AuctionEventRequest request, Long auctionId) throws AuctionEventNotFoundException;

  void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

  AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent);

  void search(String message);

  void changeStatusToStart(List<AuctionEvent> list);
}
