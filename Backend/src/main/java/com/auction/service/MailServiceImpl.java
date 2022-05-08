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
  private String[] url;

  private final MailSenderService emailSenderService;
  private final TemplateEngine templateEngine;

  @Override
  @Transactional
  @Async
  public void sendEmailConfirmation(TokenConfirmation tokenConfirmation) {
    String sign = "Lot Team";
    String userName = tokenConfirmation.getUser().getFirstName() + " " + tokenConfirmation.getUser().getLastName();
    String link = url[0] + "/login/confirm/" + tokenConfirmation.getConfirmation();

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
    String link = url[0] + "/user/update/password/" + resetPassword.getCode();

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
}
