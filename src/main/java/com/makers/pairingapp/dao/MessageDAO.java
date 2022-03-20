package com.makers.pairingapp.dao;

import com.makers.pairingapp.model.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDAO extends JpaRepository<Message, Long> {
  List<Message> findBySenderId(Long sender_id);
  List<Message> findByReceiverId(Long receiver_id);
}
