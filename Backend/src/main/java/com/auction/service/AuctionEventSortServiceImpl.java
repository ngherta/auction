package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventSort;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionEventSortRepository;
import com.auction.service.interfaces.AuctionEventSortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionEventSortServiceImpl implements AuctionEventSortService {

  private final AuctionEventSortRepository auctionEventSortRepository;
  private final AuctionEventRepository auctionEventRepository;
  private final EntityManager entityManager;

  @Override
  @Transactional
  public void sortAuctionEvent() {
    List<AuctionEventSort> auctionEventSortList = new ArrayList<>();

    List<Object[]> listOfObj = auctionEventRepository.getAuctionEventForSorting();


    for (int i = 0; i < listOfObj.size(); i++) {
      //
      AuctionEventSort auctionEventSort1;
      try{
        auctionEventSort1 = auctionEventSortRepository.findById()
      }catch (Exception e) {}
      //

      AuctionEventSort auctionEventSort = new AuctionEventSort();
      Object objectTmp[] = listOfObj.get(i);

      BigInteger auctionIdBig = (BigInteger) objectTmp[0];
      BigInteger ratingBig = (BigInteger) objectTmp[1];
      AuctionEvent auctionEvent = entityManager.getReference(AuctionEvent.class, auctionIdBig.longValue());



      auctionEventSort.setAuctionEvent(auctionEvent);
      auctionEventSort.setSortRating(ratingBig.longValue());
      auctionEventSort.setAuctionEvent(auctionEvent);

      auctionEventSortList.add(auctionEventSort);
    }

    auctionEventSortRepository.saveAll(auctionEventSortList);
  }
}
