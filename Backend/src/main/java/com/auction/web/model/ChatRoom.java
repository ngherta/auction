//package com.auction.web.model;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "chat_room")
//@NoArgsConstructor
//@Data
//public class ChatRoom {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//
//  @OneToOne
//  @EqualsAndHashCode.Exclude
//  @ToString.Exclude
//  @JoinColumn(name = "sender_id", nullable = false, updatable = false)
//  private User senderId;
//
//  @OneToOne
//  @EqualsAndHashCode.Exclude
//  @ToString.Exclude
//  @JoinColumn(name = "recipient_id", nullable = false, updatable = false)
//  private User recipientId;
//}
