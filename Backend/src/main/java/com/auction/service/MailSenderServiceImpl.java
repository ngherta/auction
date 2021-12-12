package com.auction.service;

import com.auction.service.interfaces.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
class MailSenderServiceImpl implements MailSenderService {

  @Value("${spring.mail.username}")
  private String from;
  private final JavaMailSender emailSender;
  private final SpringTemplateEngine templateEngine;


  @Override
  public void sendEmailNotification(String subject, String templateName, String... toEmails) {
    MimeMessage message = crateHtmlMessage(templateName, subject, toEmails);
    emailSender.send(message);
  }

  private MimeMessage crateHtmlMessage(String templateName, String subject, String... toEmails) {

    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper messageHelper = null;
    try {
      messageHelper = new MimeMessageHelper(message, true, "UTF-8");
      messageHelper.setFrom(from);
      messageHelper.setTo(toEmails);
      messageHelper.setSubject(subject);
      messageHelper.setText(templateName, true);
    }
    catch (MessagingException e) {
      log.info(e.getMessage());
    }
    return message;
  }
}