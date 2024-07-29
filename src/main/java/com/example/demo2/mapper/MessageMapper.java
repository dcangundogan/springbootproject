package com.example.demo2.mapper;

import com.example.demo2.dto.MessageDto;
import com.example.demo2.entitites.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageDto toDTO(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setContent(message.getContent());
        messageDto.setReceiverId(message.getReceiverId());
        messageDto.setTimestamp(message.getTimestamp());
        messageDto.setSenderId(message.getSenderId());
        messageDto.setRead(message.isRead());
        return messageDto;

    }
    public Message toEntity(MessageDto messageDto){
        Message message = new Message();
         message.setId(messageDto.getId());
        message.setContent(messageDto.getContent());
        message.setTimestamp(messageDto.getTimestamp());
        message.setReceiverId(messageDto.getReceiverId());
        message.setSenderId(messageDto.getSenderId());
        message.setRead(messageDto.isRead());
        return message;

    }
}
