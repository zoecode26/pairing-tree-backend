package com.makers.pairingapp.controller;

import com.makers.pairingapp.dao.ApplicationUserDAO;
import com.makers.pairingapp.dao.MessageDAO;
import com.makers.pairingapp.model.ApplicationUser;
import com.makers.pairingapp.model.Message;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MessageController {

  private final MessageDAO messageDAO;
  private final ApplicationUserDAO applicationUserDAO;

  public MessageController(MessageDAO messageDAO, ApplicationUserDAO applicationUserDAO) {
    this.messageDAO = messageDAO;
    this.applicationUserDAO = applicationUserDAO;
  }

  //get all messages where the current user is either the sender or receiver
  @GetMapping("/messages/{user_id}")
  List<Message> getMessages(@PathVariable(value="user_id") ApplicationUser user, Principal principal) {
    System.out.println(principal);
    //user principal to confirm the user is only able to receive messages they're involved in
//    String email = principal.getName();
//    ApplicationUser user = applicationUserDAO.findById();
    //find all the messages where the logged in user is either the sender or receiver
    List<Message> sentMessages = messageDAO.findBySenderId(user.getId());
    List<Message> receivedMessages = messageDAO.findByReceiverId(user.getId());
    //combine those together and return to the frontend
    List<Message> toReturn = new ArrayList<Message>();
    toReturn.addAll(sentMessages);
    toReturn.addAll(receivedMessages);
    return toReturn;
  }

  //create a new message
  @PostMapping("/messages")
  Message sendMessage(@RequestBody Map<String, Object> body) {
    Message message = new Message();
    message.setContent(body.get("content").toString());
    Optional<ApplicationUser> sender = applicationUserDAO.findById(Long.parseLong(body.get("sender_id").toString()));
    message.setSender(sender.get());
    Optional<ApplicationUser> receiver = applicationUserDAO.findById(Long.parseLong(body.get("receiver_id").toString()));
    message.setReceiver(receiver.get());
    message.setTime_sent(new Timestamp(System.currentTimeMillis()));
    message.setViewed(false);
    return messageDAO.save(message);
  }

  //add route to update viewed property for particular conversation
  @PostMapping("/messages/unread")
  String markAsRead(@RequestBody Map<String, Message[]> body) {
    Message[] messages = body.get("unreadMessages");
    for(Message message : messages) {
      message.setViewed(true);
      messageDAO.save(message);
    }
    return "marked as read";
  }
}

