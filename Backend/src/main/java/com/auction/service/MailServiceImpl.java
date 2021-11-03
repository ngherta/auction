package com.auction.service;

import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.ResetPasswordEntity;
import com.auction.model.User;
import com.auction.service.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

  private final JavaMailSender mailSender;

  @Override
  public void sendEmailToAuctionWinner(AuctionWinner auctionWinner)
          throws MessagingException, UnsupportedEncodingException {
    String siteURL = "http://localhost:8080/";
    String toAddress = auctionWinner.getUser().getEmail();
    String fromAddress = "gherta.nicolai@gmail.com";
    String senderName = "LOT";
    String subject = "Auction" + "xxx";
    String content = "Dear [[name]],<br>"
            + "You won auction - " + auctionWinner.getAuctionEvent().getTitle() + "<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">" + auctionWinner.getAuctionEvent().getTitle() + "</a></h3>"
            + "Thank you,<br>"
            + "LOT.";

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(fromAddress, senderName);
    helper.setTo(toAddress);
    helper.setSubject(subject);

    content = content.replace("[[name]]", auctionWinner.getUser().getFirstName() + " " + auctionWinner.getAuctionEvent().getUser().getLastName());
    String auctionUrl = siteURL + "auction/" + auctionWinner.getAuctionEvent().getId();

    content = content.replace("[[URL]]", auctionUrl);

    helper.setText(content, true);

    mailSender.send(message);
  }

  @Override
  public void sendEmailToAuctionParticipant(List<AuctionAction> listOfAuctionAction)
          throws MessagingException, UnsupportedEncodingException {
    String siteURL = "http://localhost:8080/";
    String fromAddress = "gherta.nicolai@gmail.com";
    String senderName = "LOT";
    String subject = "Auction - " + listOfAuctionAction.get(0).getAuctionEvent().getTitle();
    String content;

    for (AuctionAction action : listOfAuctionAction) {
      String toAddress = action.getUser().getEmail();
      content = "Dear [[name]],<br>"
              + "Auction - " + action.getAuctionEvent().getTitle() + "finished." +  "<br>"
              + "<h3><a href=\"[[URL]]\" target=\"_self\">" + action.getAuctionEvent().getTitle() + "</a></h3>"
              + "Thank you,<br>"
              + "LOT.";

      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setFrom(fromAddress, senderName);
      helper.setTo(toAddress);
      helper.setSubject(subject);

      content = content.replace("[[name]]", action.getUser().getFirstName() + " " + action.getUser().getLastName());
      String auctionUrl = siteURL + "auction/" + action.getAuctionEvent().getId();

      content = content.replace("[[URL]]", auctionUrl);

      helper.setText(content, true);

      mailSender.send(message);
    }
  }
}