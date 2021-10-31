package com.auction.service.interfaces;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;
import com.auction.dto.request.AuctionFinishByFinishPriceRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;

import java.util.List;

public interface AuctionEventService {
    AuctionEventDto save(AuctionEventRequest request);

    void changeStatusToFinished(List<AuctionEvent> list);

    void finishByFinishPrice(AuctionFinishByFinishPriceRequest request) throws AuctionEventNotFoundException, UserNotFoundException;

    List<AuctionEventDto> getAllSortByRating();

    List<AuctionEventDto> getAll();
}
