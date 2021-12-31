package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.enums.AuctionStatus;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import com.auction.web.dto.request.AuctionFinishByFinishPriceRequest;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AuctionEventService {
  AuctionEventDto save(AuctionEventRequest request);

  void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException;

  void finishByFinishPrice(AuctionFinishByFinishPriceRequest request);

  Page<AuctionEventDto> getAllSortByRating(int page, int perPage);

  Page<AuctionEventDto> get(int page, int perPage);

  AuctionEventDto blockAuctionEventById(Long auctionId);

  void delete(AuctionEvent auctionEvent);

  void deleteById(Long auctionId);

  AuctionEventDto update(AuctionEventRequest request, Long auctionId);

  void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

  AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent);

  void search(String message);

  void changeStatusToStart(List<AuctionEvent> list);

  AuctionEvent findById(Long id);

  AuctionEventDto getById(Long auctionId);

  Page<AuctionEventDto> findAuctionsByCategory(Long categoryId, int page, int perPage);

  List<AuctionEventDto> findAll();

  List<AuctionEvent> getListForStartOrFinish(AuctionStatus status);
}
