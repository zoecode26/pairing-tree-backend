package com.makers.pairingapp.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  private Timestamp time_sent;

  private Boolean viewed;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private ApplicationUser sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private ApplicationUser receiver;

  public Message(String content, Timestamp time_sent, Boolean viewed, ApplicationUser sender, ApplicationUser receiver) {
    this.content = content;
    this.time_sent = time_sent;
    this.viewed = viewed;
    this.sender = sender;
    this.receiver = receiver;
  }

  public Message() { }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getTime_sent() {
    return time_sent;
  }

  public void setTime_sent(Timestamp time_sent) {
    this.time_sent = time_sent;
  }

  public Boolean getViewed() {
    return viewed;
  }

  public void setViewed(Boolean viewed) {
    this.viewed = viewed;
  }

  public ApplicationUser getSender() {
    return sender;
  }

  public void setSender(ApplicationUser sender) {
    this.sender = sender;
  }

  public ApplicationUser getReceiver() {
    return receiver;
  }

  public void setReceiver(ApplicationUser receiver) {
    this.receiver = receiver;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Message message = (Message) o;

    return id.equals(message.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Message{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", time_sent=" + time_sent +
            ", viewed=" + viewed +
            ", sender=" + sender +
            ", receiver=" + receiver +
            '}';
  }
}
