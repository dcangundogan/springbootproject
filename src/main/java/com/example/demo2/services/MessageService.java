package com.example.demo2.services;

import com.example.demo2.dto.MessageDto;
import com.example.demo2.entitites.Message;
import com.example.demo2.mapper.MessageMapper;
import com.example.demo2.repostories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;

    public List<MessageDto> getInboxMessages(UUID userId) {
        return messageRepository.findByReceiverId(userId)
                .stream()
                .map(message -> {
                    message.setRead(true);
                    messageRepository.save(message);
                    return messageMapper.toDTO(message);
                })
                .collect(Collectors.toList());
    }
    public List<MessageDto> getMessagesByParentId(UUID parentId) {
        return messageRepository.findByParentId(parentId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MessageDto sendMessage(MessageDto messageDTO) {
        Message message = messageMapper.toEntity(messageDTO);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDTO(savedMessage);
    }

    public List<MessageDto> getMessagesBetweenUsers(UUID senderId, UUID receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<MessageDto> getMessagesBySender(UUID senderId) {
        return messageRepository.findBySenderId(senderId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDto> getMessagesByReceiver(UUID receiverId) {
        return messageRepository.findByReceiverId(receiverId)
                .stream()
                .map(messageMapper::toDTO)
                .collect(Collectors.toList());
    }



}
