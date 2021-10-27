package com.auction.service;

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
    Map<AuctionEvent, Double> map = new HashMap<>();

    List<Object> asd = (List<Object>) entityManager.createQuery(
            "SELECT a.* ," +
                    "       (SELECT count(*) FROM auction_action aa WHERE aa.auction_id = a.id) as rating " +
                    "FROM auction_table AS a" +
                    "                    ORDER BY rating");

    for (Map.Entry<AuctionEvent,Double> entry : map.entrySet()) {
      AuctionEventSort auctionEventSort = new AuctionEventSort();

      auctionEventSort.setAuctionEvent(entry.getKey());
      auctionEventSort.setSortRating(entry.getValue());

      auctionEventSortList.add(auctionEventSort);
    }

    auctionEventSortRepository.saveAll(auctionEventSortList);
  }
}
