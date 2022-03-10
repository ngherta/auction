package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.model.User;
import com.auction.web.dto.AuctionWinnerDto;
import com.auction.web.dto.request.AddAddressToWinnerRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuctionWinnerService {

  Page<AuctionWinnerDto> getAllAuctionWinnerForUser(User user, int page, int perPage);

  Page<AuctionWinnerDto> getAllAuctionWinnerForUser(Long userId, int page, int perPage);

  void createForFinishPrice(AuctionEvent auctionEvent, User user);

  void unPaid(AuctionWinner auctionWinner);

  AuctionWinner create(AuctionEvent event, User user, Double bet);

  void paid(PaymentOrder paymentOrder);

  void addAddress(AddAddressToWinnerRequest request);

  void useDefaultAddress(Long auctionId);

  void startDelivery(AuctionWinner auctionWinner);

  void finishDelivery(AuctionWinner auctionWinner);
}
