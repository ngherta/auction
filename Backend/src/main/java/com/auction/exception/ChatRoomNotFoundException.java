package com.auction.exception;

public class ChatRoomNotFoundException extends AuctionRuntimeException{
  public ChatRoomNotFoundException(String message) {
    super(message);
  }
}
