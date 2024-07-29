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
    @GetMapping("/between")
    public List<MessageDto> getMessagesBetweenUsers(@RequestParam UUID senderId, @RequestParam UUID receiverId) {
        return messageService.getMessagesBetweenUsers(senderId, receiverId);
    }

    @GetMapping("/sent")
    public List<MessageDto> getMessagesBySender(@RequestParam UUID senderId) {
        return messageService.getMessagesBySender(senderId);
    }

    @GetMapping("/received")
    public List<MessageDto> getMessagesByReceiver(@RequestParam UUID receiverId) {
        return messageService.getMessagesByReceiver(receiverId);
    }
}
