package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventSort;
import com.auction.projection.AuctionEventSortProjection;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionEventSortRepository;
import com.auction.service.interfaces.AuctionEventSortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
class AuctionEventSortServiceImpl implements AuctionEventSortService {

  private final AuctionEventSortRepository auctionEventSortRepository;
  private final AuctionEventRepository auctionEventRepository;

  @Override
  @Transactional
  public void sortAuctionEvent() {
    List<AuctionEventSort> auctionEventSortList = new ArrayList<>();

    List<AuctionEventSortProjection> list = auctionEventRepository.getAuctionEventForSorting();


    for (AuctionEventSortProjection e : list) {
      BigInteger auctionIdBig = e.getAuctionId();
      BigInteger ratingBig = e.getCount();
      Optional<AuctionEventSort> auctionEventSortCheck;
      auctionEventSortCheck = auctionEventSortRepository.findById(auctionIdBig.longValue());

      AuctionEventSort auctionEventSort = AuctionEventSort.builder()
              .sortRating(ratingBig.longValue())
              .build();

      if (auctionEventSortCheck.isPresent()) {
        auctionEventSort = auctionEventSortCheck.get();
      }
      else {
        AuctionEvent auctionEvent = auctionEventRepository.findById(auctionIdBig.longValue())
                .orElseThrow(() ->
                                     new AuctionEventNotFoundException("AuctionEvent[" + auctionIdBig.longValue() + "] doesn't exist!!"));
        auctionEventSort.setAuctionEvent(auctionEvent);
      }

      auctionEventSortList.add(auctionEventSort);
    }

    auctionEventSortRepository.saveAll(auctionEventSortList);
  }
}
