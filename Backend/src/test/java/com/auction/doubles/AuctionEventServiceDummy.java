package com.auction.doubles;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.AuctionSearchDto;
import com.auction.web.dto.request.AuctionEventRequest;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class AuctionEventServiceDummy implements AuctionEventService {
  @Override
  public AuctionEventDto save(AuctionEventRequest request) {
    return null;
  }

  @Override
  public void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException {

  }

  @Override
  public void finishByFinishPrice(AuctionEvent auctionEvent, User user) {

  }

  @Override
  public AuctionEvent finish(AuctionEvent auctionEvent) {
    return null;
  }

  @Override
  public Page<AuctionEventDto> getAllSortByRating(int page, int perPage) {
    return null;
  }

  @Override
  public Page<AuctionEventDto> get(int page, int perPage) {
    return null;
  }

  @Override
  public AuctionEventDto blockAuctionEventById(Long auctionId) {
    return null;
  }

  @Override
  public void delete(AuctionEvent auctionEvent) {

  }

  @Override
  public void deleteById(Long auctionId) {

  }

  @Override
  public void resetAuction(AuctionEvent auctionEvent, boolean actions) {

  }

  @Override
  public AuctionEventDto update(AuctionEventRequest request, Long auctionId) {
    return null;
  }

  @Override
  public void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException {

  }

  @Override
  public AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent) {
    return null;
  }

  @Override
  public Page<AuctionEventDto> search(String message, int page, int perPage) {
    return null;
  }

  @Override
  public void changeStatusToStart(List<AuctionEvent> list) {

  }

  @Override
  public AuctionEvent findById(Long id) {
    return null;
  }

  @Override
  public AuctionEventDto getById(Long auctionId) {
    return null;
  }

  @Override
  public Page<AuctionEventDto> findAuctionsByCategory(Long categoryId, int page, int perPage) {
    return null;
  }

  @Override
  public List<AuctionEventDto> findAll() {
    return null;
  }

  @Override
  public List<AuctionEvent> getListForStart(AuctionStatus status) {
    return null;
  }

  @Override
  public List<AuctionEvent> getListForFinish(AuctionStatus status) {
    return null;
  }

  @Override
  public Page<AuctionEventDto> findAllAndFilter(int page, int perPage, String title, List<Long> categoriesIds, List<AuctionStatus> statuses) {
    return null;
  }

  @Override
  public Page<AuctionEventDto> getAllByOwner(Long userId, int page, int perPage) {
    return null;
  }

  @Override
  public List<AuctionSearchDto> reactiveSearch(String message) {
    return null;
  }

  @Override
  public Page<AuctionEventDto> getAuctionsByParticipant(Long userId, int page, int perPage) {
    return null;
  }
}
