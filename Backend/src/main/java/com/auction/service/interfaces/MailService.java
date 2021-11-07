package com.auction.service.interfaces;

import com.auction.web.model.AuctionAction;
import com.auction.web.model.AuctionWinner;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MailService {

  void sendEmailToAuctionParticipant(List<AuctionAction> listOfAuctionAction) throws MessagingException, UnsupportedEncodingException;

  void sendEmailToAuctionWinner (AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

}
