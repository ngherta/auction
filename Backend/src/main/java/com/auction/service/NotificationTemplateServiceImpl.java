package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.AuctionWinner;
import com.auction.model.NotificationMessage;
import com.auction.model.enums.ComplaintStatus;
import com.auction.model.enums.NotificationType;
import com.auction.repository.NotificationMessageRepository;
import com.auction.service.interfaces.NotificationGenerationService;
import com.auction.service.interfaces.NotificationMessageService;
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
  private final NotificationMessageService notificationMessageService;

  @Override
  @Transactional
  public void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent) {
    StringBuilder sb = new StringBuilder();
    sb.append(auctionEvent.getUser().getFirstName());
    sb.append(" ");
    sb.append(auctionEvent.getUser().getLastName());
    sb.append(" created new auction <b>");
    sb.append(auctionEvent.getTitle());
    sb.append("</b>.");
    NotificationMessage message = NotificationMessage
            .builder()
            .message(sb.toString())
            .genDate(LocalDateTime.now())
            .singleNotification(false)
            .type(NotificationType.CREATING_AUCTION)
            .build();

    message = notificationMessageRepository.save(message);
    notificationGenerationService.generateNotificationsForActiveUsers(message);
  }

  @Override
  @Transactional
  public void sendNotificationOfFinishingAuction(AuctionWinner auctionWinner) {
    StringBuilder sb = new StringBuilder();
    sb.append("Auction - <b>");
    sb.append(auctionWinner.getAuctionEvent().getTitle());
    sb.append("</b> ended. Last bid - <b>");
    sb.append(auctionWinner.getPrice());
    sb.append("</b> by");
    sb.append(auctionWinner.getUser().getFirstName());
    sb.append(" ");
    sb.append(auctionWinner.getUser().getLastName());
    sb.append(".");

    NotificationMessage message = NotificationMessage
            .builder()
            .genDate(LocalDateTime.now())
            .message(sb.toString())
            .singleNotification(false)
            .type(NotificationType.FINISHING_AUCTION)
            .build();

    message = notificationMessageRepository.save(message);
    notificationGenerationService.generateNotificationsForActiveUsers(message);
  }

  @Override
  public void sendNotificationOfComplaintAnswer(AuctionEventComplaintAudit audit) {
    StringBuilder sb = new StringBuilder();
    if (audit.getComplaintStatus() == ComplaintStatus.SATISFIED) {
      sb.append("Complaint #");
      sb.append(audit.getAuctionEventComplaint().getId());
      sb.append(" was satisfied and auction - <b>");
      sb.append(audit.getAuctionEventComplaint().getAuctionEvent().getId());
      sb.append("</b> was blocked!<br>");
      sb.append("Thank you for your feedback!");
    }
    else if (audit.getComplaintStatus() == ComplaintStatus.REJECTED) {
      sb.append("We check your complaint <b>");
      sb.append(audit.getAuctionEventComplaint().getId());
      sb.append("</b> and we didn't find anything strange.");
      sb.append("Thank you for your feedback!");
    }
    NotificationMessage notificationMessage = NotificationMessage
            .builder()
            .singleNotification(true)
            .message(sb.toString())
            .type(NotificationType.COMPLAINT_ANSWER)
            .genDate(LocalDateTime.now())
            .build();

    notificationGenerationService.generateSingleNotificationFor(audit.getAuctionEventComplaint().getUser(), notificationMessage);
  }
}
