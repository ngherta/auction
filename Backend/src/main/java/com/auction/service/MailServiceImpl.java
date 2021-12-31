package com.auction.service;

import com.auction.model.AuctionAction;
import com.auction.model.AuctionWinner;
import com.auction.model.ResetPasswordEntity;
import com.auction.model.TokenConfirmation;
import com.auction.model.User;
import com.auction.service.interfaces.MailSenderService;
import com.auction.service.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class MailServiceImpl implements MailService {

  @Value("${spring.client.url}")
  private String url;

  private final MailSenderService emailSenderService;
  private final TemplateEngine templateEngine;

  @Override
  @Transactional
  @Async
  public void sendEmailConfirmation(TokenConfirmation tokenConfirmation) {
    String sign = "Lot Team";
    String userName = tokenConfirmation.getUser().getFirstName() + " " + tokenConfirmation.getUser().getLastName();
    String link = url + "/users/verify?code=" + tokenConfirmation.getConfirmation();

    Context context = new Context();
    context.setVariable("sign",sign);
    context.setVariable("userName", userName);
    context.setVariable("link", link);
    context.setVariable("logo","images/logo.png");
    String templateName = templateEngine.process("emailConfirmationTemplate.html", context);
    try {
      emailSenderService.sendEmailNotification("Email confirmation", templateName, tokenConfirmation.getUser().getEmail());
    } catch (MessagingException e) {
      log.info("Message sender exception: message[{}], cause[{}]", e.getMessage(),e.getCause());
    }
  }

  @Override
  @Transactional
  @Async
  public void sendEmailForResetPassword(User user, ResetPasswordEntity resetPassword) {
    String sign = "Lot Team";
    String userName = user.getFirstName() + " " + user.getLastName();
    String link = url + "/user/update/password/" + resetPassword.getCode();

    Context context = new Context();
    context.setVariable("sign",sign);
    context.setVariable("userName", userName);
    context.setVariable("link", link);
    context.setVariable("logo","images/logo.png");
    String templateName = templateEngine.process("forgotPasswordTemplate.html", context);
    try {
      emailSenderService.sendEmailNotification("Reset password", templateName, user.getEmail());
    } catch (MessagingException e) {
      log.info(e.getMessage());
    }
  }

  @Override
  public void sendEmailToAuctionWinner(AuctionWinner auctionWinner)
          throws MessagingException, UnsupportedEncodingException {
//    String toAddress = auctionWinner.getUser().getEmail();
//    String fromAddress = "gherta.nicolai@gmail.com";
//    String senderName = "LOT";
//    String subject = "Auction" + "xxx";
//    String content = "Dear [[name]],<br>"
//            + "You won auction - " + auctionWinner.getAuctionEvent().getTitle() + "<br>"
//            + "<h3><a href=\"[[URL]]\" target=\"_self\">" + auctionWinner.getAuctionEvent().getTitle() + "</a></h3>"
//            + "Thank you,<br>"
//            + "LOT.";
//
//    MimeMessage message = mailSender.createMimeMessage();
//    MimeMessageHelper helper = new MimeMessageHelper(message);
//
//    helper.setFrom(fromAddress, senderName);
//    helper.setTo(toAddress);
//    helper.setSubject(subject);
//
//    content = content.replace("[[name]]", auctionWinner.getUser().getFirstName() + " " + auctionWinner.getAuctionEvent().getUser().getLastName());
//    String auctionUrl = siteURL + "auction/" + auctionWinner.getAuctionEvent().getId();
//
//    content = content.replace("[[URL]]", auctionUrl);
//
//    helper.setText(content, true);
//    mailSender.send(message);
  }

  @Override
  public void sendEmailToAuctionParticipant(List<AuctionAction> listOfAuctionAction)
          throws MessagingException, UnsupportedEncodingException {
//    String fromAddress = "gherta.nicolai@gmail.com";
//    String senderName = "LOT";
//    String subject = "Auction - " + listOfAuctionAction.get(0).getAuctionEvent().getTitle();
//    String content;
//
//    for (AuctionAction action : listOfAuctionAction) {
//      String toAddress = action.getUser().getEmail();
//      content = "Dear [[name]],<br>"
//              + "Auction - " + action.getAuctionEvent().getTitle() + "finished." +  "<br>"
//              + "<h3><a href=\"[[URL]]\" target=\"_self\">" + action.getAuctionEvent().getTitle() + "</a></h3>"
//              + "Thank you,<br>"
//              + "LOT.";
//
//      MimeMessage message = mailSender.createMimeMessage();
//      MimeMessageHelper helper = new MimeMessageHelper(message);
//
//      helper.setFrom(fromAddress, senderName);
//      helper.setTo(toAddress);
//      helper.setSubject(subject);
//
//      content = content.replace("[[name]]", action.getUser().getFirstName() + " " + action.getUser().getLastName());
//      String auctionUrl = siteURL + "auction/" + action.getAuctionEvent().getId();
//
//      content = content.replace("[[URL]]", auctionUrl);
//
//      helper.setText(content, true);
//
//      mailSender.send(message);
//    }
  }
}
