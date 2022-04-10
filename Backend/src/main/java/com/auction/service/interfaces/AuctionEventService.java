package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.AuctionSearchDto;
import com.auction.web.dto.request.AuctionEventRequest;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AuctionEventService {
  AuctionEventDto save(AuctionEventRequest request);

  void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException;

  void finishByFinishPrice(AuctionEvent auctionEvent, User user);

  AuctionEvent finish(AuctionEvent auctionEvent);

  Page<AuctionEventDto> getAllSortByRating(int page, int perPage);

  Page<AuctionEventDto> get(int page, int perPage);

  AuctionEventDto blockAuctionEventById(Long auctionId);

  void delete(AuctionEvent auctionEvent);

  void deleteById(Long auctionId);

  void resetAuction(AuctionEvent auctionEvent, boolean actions);

  AuctionEventDto update(AuctionEventRequest request, Long auctionId);

  void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

  AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent);

  Page<AuctionEventDto> search(String message, int page, int perPage);

  void changeStatusToStart(List<AuctionEvent> list);

  AuctionEvent findById(Long id);

  AuctionEventDto getById(Long auctionId);

  Page<AuctionEventDto> findAuctionsByCategory(Long categoryId, int page, int perPage);

  List<AuctionEventDto> findAll();

  List<AuctionEvent> getListForStart(AuctionStatus status);

  List<AuctionEvent> getListForFinish(AuctionStatus status);

  Page<AuctionEventDto> findAllAndFilter(int page,
                                         int perPage,
                                         String title,
                                         List<Long> categoriesIds,
                                         List<AuctionStatus> statuses);


  Page<AuctionEventDto> getAllByOwner(Long userId,
                                      int page,
                                      int perPage);

  List<AuctionSearchDto> reactiveSearch(String message);

  Page<AuctionEventDto> getAuctionsByParticipant(Long userId, int page, int perPage);
}
