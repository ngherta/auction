package com.auction.service.interfaces;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;

public interface AuctionEventService {
    AuctionEventDto save(AuctionEventRequest request);
}
