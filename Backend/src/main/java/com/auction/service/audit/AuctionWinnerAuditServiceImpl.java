package com.auction.service.audit;

import com.auction.model.AuctionWinner;
import com.auction.model.AuctionWinnerAudit;
import com.auction.repository.AuctionWinnerAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuctionWinnerAuditServiceImpl implements AuctionWinnerAuditService{
  private final AuctionWinnerAuditRepository auctionWinnerAuditRepository;

  @Override
  @Transactional
  public void create(AuctionWinner auctionWinner) {
    AuctionWinnerAudit audit = AuctionWinnerAudit
        .builder()
        .auctionWinner(auctionWinner)
        .country(auctionWinner.getCountry())
        .city(auctionWinner.getCity())
        .trackNumber(auctionWinner.getTrackNumber())
        .status(auctionWinner.getStatus())
        .address(auctionWinner.getAddress())
        .created(LocalDateTime.now())
        .build();

    auctionWinnerAuditRepository.save(audit);
  }
}
