package com.example.demo2.controllers;

import com.example.demo2.dto.MessageDto;
import com.example.demo2.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;



    @PostMapping("/send")
    public MessageDto sendMessage(@RequestBody MessageDto messageDto) {
        return messageService.sendMessage(messageDto);
    }
    @GetMapping("/between/{senderId}/{receiverId}")
    public List<MessageDto> getMessagesBetweenUsers(@PathVariable UUID senderId, @PathVariable UUID receiverId) {
        return messageService.getMessagesBetweenUsers(senderId, receiverId);
    }

    @GetMapping("/sent/{senderId}")
    public List<MessageDto> getMessagesBySender(@PathVariable UUID senderId) {
        return messageService.getMessagesBySender(senderId);
    }

    @GetMapping("/received/{receiverId}" )
    public List<MessageDto> getMessagesByReceiver(@PathVariable UUID receiverId) {
        return messageService.getMessagesByReceiver(receiverId);
    }
}
