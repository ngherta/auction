package com.auction.service.interfaces;

import javax.mail.MessagingException;

public interface MailSenderService {

  void sendEmailNotification(String subject, String templateName, String... toEmails) throws MessagingException;
}
