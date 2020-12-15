package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Long> {
  List<Message> findBySenderId(Long sender_id);
  List<Message> findByReceiverId(Long receiver_id);
}
