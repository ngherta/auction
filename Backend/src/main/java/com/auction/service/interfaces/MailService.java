package com.auction.service.interfaces;

import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MailService {

  void sendEmailToAuctionParticipant(List<AuctionAction> listOfAuctionAction) throws MessagingException, UnsupportedEncodingException;

  void sendEmailToAuctionWinner (AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

}
