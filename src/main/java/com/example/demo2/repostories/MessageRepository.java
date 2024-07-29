package com.example.demo2.repostories;

import com.example.demo2.entitites.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findBySenderId(UUID senderId);
    List<Message> findByReceiverId(UUID receiverId);
    List<Message> findBySenderIdAndReceiverId(UUID senderId, UUID receiverId);
}
