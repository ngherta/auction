package com.auction.service.interfaces;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;

import java.util.List;

public interface AuctionEventService {
    AuctionEventDto save(AuctionEventRequest request);

    void changeStatusToFinished(List<Long> list);
}
