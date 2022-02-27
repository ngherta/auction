package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.model.User;
import com.auction.web.dto.AuctionWinnerDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuctionWinnerService {

  Page<AuctionWinnerDto> getAllAuctionWinnerForUser(User user, int page, int perPage);

  Page<AuctionWinnerDto> getAllAuctionWinnerForUser(Long userId, int page, int perPage);

  void createForFinishPrice(AuctionEvent auctionEvent, User user);

  void unPaid(AuctionWinner auctionWinner);

  AuctionWinner create(AuctionEvent event, User user, Double bet);

  void paid(PaymentOrder paymentOrder);
}
