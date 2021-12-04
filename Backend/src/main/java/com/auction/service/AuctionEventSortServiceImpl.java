package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventSort;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionEventSortRepository;
import com.auction.service.interfaces.AuctionEventSortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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

    List<Object[]> listOfObj = auctionEventRepository.getAuctionEventForSorting();


    for (int i = 0; i < listOfObj.size(); i++) {
      Object objectTmp[] = listOfObj.get(i);

      BigInteger auctionIdBig = (BigInteger) objectTmp[0];
      BigInteger ratingBig = (BigInteger) objectTmp[1];
      Optional<AuctionEventSort> auctionEventSortCheck;
      auctionEventSortCheck = auctionEventSortRepository.findById(auctionIdBig.longValue());

      AuctionEventSort auctionEventSort = AuctionEventSort.builder()
              .sortRating(ratingBig.longValue())
              .build();

      if (auctionEventSortCheck.isPresent()) {
        auctionEventSort = auctionEventSortCheck.get();
      } else {
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
