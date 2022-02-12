package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.NotificationMessage;
import com.auction.model.enums.NotificationType;
import com.auction.repository.NotificationMessageRepository;
import com.auction.service.interfaces.NotificationGenerationService;
import com.auction.service.interfaces.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

  private final NotificationGenerationService notificationGenerationService;
  private final NotificationMessageRepository notificationMessageRepository;

  @Override
  @Transactional
  public void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent) {
    StringBuilder sb = new StringBuilder();
    sb.append(auctionEvent.getUser().getFirstName());
    sb.append(" ");
    sb.append(auctionEvent.getUser().getLastName());
    sb.append(" created new auction ");
    sb.append(auctionEvent.getTitle());
    sb.append(".");
    NotificationMessage message = NotificationMessage
            .builder()
            .message(sb.toString())
            .genDate(LocalDateTime.now())
            .type(NotificationType.CREATING_AUCTION)
            .build();

    message = notificationMessageRepository.save(message);
    notificationGenerationService.generateNotificationsForActiveUsers(message);
  }

  @Override
  @Transactional
  public void sendNotificationOfFinishingAuction(AuctionWinner auctionWinner) {
    StringBuilder sb = new StringBuilder();
    sb.append("Auction - ");
    sb.append(auctionWinner.getAuctionEvent().getTitle());
    sb.append(" ended. Last bid - ");
    sb.append(auctionWinner.getPrice());
    sb.append(" by");
    sb.append(auctionWinner.getUser().getFirstName());
    sb.append(" ");
    sb.append(auctionWinner.getUser().getLastName());
    sb.append(".");

    NotificationMessage message = NotificationMessage
            .builder()
            .genDate(LocalDateTime.now())
            .message(sb.toString())
            .type(NotificationType.FINISHING_AUCTION)
            .build();

    message = notificationMessageRepository.save(message);
    notificationGenerationService.generateNotificationsForActiveUsers(message);
  }
}
