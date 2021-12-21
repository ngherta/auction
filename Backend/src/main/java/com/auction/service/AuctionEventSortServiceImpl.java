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
    if (list.isEmpty()) return;

    for (AuctionEventSortProjection e : list) {
      Optional<AuctionEventSort> auctionEventSortCheck =
              auctionEventSortRepository.findById(e.getAuctionId());

      AuctionEventSort auctionEventSort = AuctionEventSort.builder()
              .sortRating(e.getCount())
              .build();

      if (auctionEventSortCheck.isPresent()) {
        auctionEventSort = auctionEventSortCheck.get();
        auctionEventSort.setSortRating(e.getCount());
      }
      else {
        AuctionEvent auctionEvent = auctionEventRepository.findById(e.getAuctionId())
                .orElseThrow(() ->
                                     new AuctionEventNotFoundException("AuctionEvent[" + e.getAuctionId() + "] doesn't exist!!"));
        auctionEventSort.setAuctionEvent(auctionEvent);
      }

      auctionEventSortList.add(auctionEventSort);
    }

    auctionEventSortRepository.saveAll(auctionEventSortList);
  }
}
