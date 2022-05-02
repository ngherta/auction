package com.auction.service;

import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.AuctionWinner;
import com.auction.model.NotificationMessage;
import com.auction.model.User;
import com.auction.model.enums.ComplaintStatus;
import com.auction.model.enums.NotificationType;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.NotificationMessageRepository;
import com.auction.service.interfaces.ImageResizeService;
import com.auction.service.interfaces.NotificationGenerationService;
import com.auction.service.interfaces.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class NotificationTemplateServiceImpl implements NotificationTemplateService {

  private final NotificationGenerationService notificationGenerationService;
  private final NotificationMessageRepository notificationMessageRepository;
  private final AuctionActionRepository auctionActionRepository;
  private final ImageResizeService imageResizeService;

  private static final String DEFAULT_IMAGE = "IMAGE_LINK-NOT-IMPL";

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

    String image;
    if (!auctionEvent.getImages().isEmpty()) {
      image = imageResizeService
              .resize(auctionEvent
                              .getImages()
                              .get(0), 200);
    }
    else {
      image = DEFAULT_IMAGE;
    }

    NotificationMessage message = NotificationMessage
            .builder()
            .message(sb.toString())
            .genDate(LocalDateTime.now())
            .image(image)
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
    sb.append("</b> finished. Last bid - <b>");
    sb.append(new DecimalFormat("#0.00").format(auctionWinner.getPrice()));
    sb.append("$</b> by");
    sb.append(auctionWinner.getUser().getFirstName());
    sb.append(" ");
    sb.append(auctionWinner.getUser().getLastName());
    sb.append(".");

    String image;
    if (!auctionWinner.getAuctionEvent().getImages().isEmpty()) {
      image = imageResizeService
              .resize(auctionWinner
                              .getAuctionEvent()
                              .getImages()
                              .get(0), 200);
    }
    else {
      image = DEFAULT_IMAGE;
    }

    NotificationMessage message = NotificationMessage
            .builder()
            .genDate(LocalDateTime.now())
            .message(sb.toString())
            .image(image)
            .singleNotification(false)
            .type(NotificationType.FINISHING_AUCTION)
            .build();

    message = notificationMessageRepository.save(message);
    notificationGenerationService.generateNotificationsForActiveUsers(message);
  }

  @Override
  @Transactional
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

    String image;
    if (!audit.getAuctionEventComplaint().getAuctionEvent().getImages().isEmpty()) {
      image = imageResizeService
              .resize(audit
                              .getAuctionEventComplaint()
                              .getAuctionEvent()
                              .getImages()
                              .get(0), 200);
    }
    else {
      image = DEFAULT_IMAGE;
    }

    NotificationMessage notificationMessage = NotificationMessage
            .builder()
            .singleNotification(true)
            .message(sb.toString())
            .image(image)
            .type(NotificationType.COMPLAINT_ANSWER)
            .genDate(LocalDateTime.now())
            .build();

    notificationGenerationService.generateSingleNotificationFor(audit.getAuctionEventComplaint().getUser(), notificationMessage);
  }

  @Override
  @Transactional
  public void sendNotificationOfChangeBet(AuctionAction currentAction) {
    StringBuilder sb = new StringBuilder();
    sb.append("New bet - <b>");
    sb.append(new DecimalFormat("#0.00").format(currentAction.getBet()));
    sb.append("$</b> by ");
    sb.append(currentAction.getUser().getFirstName());
    sb.append(" ");
    sb.append(currentAction.getUser().getLastName());
    sb.append(" for auction - ");
    sb.append("<b>");
    sb.append(currentAction.getAuctionEvent().getTitle());
    sb.append("</b>");
    sb.append(".");

    String image;

    if (!currentAction.getAuctionEvent().getImages().isEmpty()) {
      image = imageResizeService
              .resize(currentAction
                              .getAuctionEvent()
                              .getImages()
                              .get(0), 200);
    }
    else {
      image = DEFAULT_IMAGE;
    }

    NotificationMessage message = NotificationMessage
            .builder()
            .genDate(LocalDateTime.now())
            .message(sb.toString())
            .image(image)
            .singleNotification(true)
            .type(NotificationType.BET_CHANGED)
            .build();


    message = notificationMessageRepository.save(message);

    List<AuctionAction> participants = auctionActionRepository.getAllByAuctionGroupByUser(currentAction.getAuctionEvent().getId());
    List<User> users = participants
            .stream()
            .map(AuctionAction::getUser)
            .collect(Collectors.toList());

    notificationGenerationService.generateSingleNotificationFor(users, message);
  }

  @Override
  @Transactional
  public void sendNotificationOfCreatingComplaint(AuctionEventComplaint complaint) {
    StringBuilder sb = new StringBuilder();
    sb.append("Complaint for auction - <b>");
    sb.append(complaint.getAuctionEvent().getId());
    sb.append("</b> was created by ");
    sb.append(complaint.getUser().getFirstName());
    sb.append(" ");
    sb.append(complaint.getUser().getLastName());
    sb.append(".");

    String image;
    if (!complaint.getAuctionEvent().getImages().isEmpty()) {
      image = imageResizeService
          .resize(complaint
                      .getAuctionEvent()
                      .getImages()
                      .get(0), 200);
    }
    else {
      image = DEFAULT_IMAGE;
    }

    NotificationMessage message = NotificationMessage
        .builder()
        .genDate(LocalDateTime.now())
        .message(sb.toString())
        .image(image)
        .singleNotification(true)
        .type(NotificationType.COMPLAINT_CREATING)
        .build();

    message = notificationMessageRepository.save(message);

    notificationGenerationService.generateNotificationForAdmins(message);
  }
}
