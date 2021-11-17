package com.auction.service.interfaces;

import com.auction.web.model.AuctionAction;
import com.auction.web.model.AuctionWinner;
import com.auction.web.model.TokenConfirmation;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MailService {

  void sendEmailToAuctionParticipant(List<AuctionAction> listOfAuctionAction) throws MessagingException, UnsupportedEncodingException;

  void sendEmailToAuctionWinner (AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException;

  void sendConfirmation(TokenConfirmation confirmation) throws MessagingException, UnsupportedEncodingException;
}
