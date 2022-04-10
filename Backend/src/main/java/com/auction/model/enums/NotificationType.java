package com.auction.model.enums;

public enum NotificationType {
  CREATING_AUCTION("Creating auction", "Do you want to receive notifications about new auction?"),
  FINISHING_AUCTION("Finishing auction", "Do you want to receive notifications about auctions where you participate?"),
  NEW_MESSAGE("New messages", ""),
  BET_CHANGED("Bet changing", "Do you want to receive notifications when somebody canceled your bet?"),
  COMPLAINT_ANSWER("Complaint", "Do you want to receive notification about your complaints?"),
  COMPLAINT_CREATING("Create new complaint", "Do you want to receive notification about complaint creation?");

  private final String value;
  private final String description;

  NotificationType(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String getValue() {
    return this.value;
  }

  public String getDescription() {
    return this.description;
  }
}
