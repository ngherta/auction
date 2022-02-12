package com.auction.model.enums;

public enum NotificationType {
  CREATING_AUCTION("Creating auction"),
  FINISHING_AUCTION("Finishing auction"),
  NEW_MESSAGE("New messages"),
  BET_CHANGED("Bet changing"),
  COMPLAINT_ANSWER("Complaint");

  private String value;

  NotificationType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
