import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../services/message.service';
import { AuthService } from '../services/auth.service';
import {NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {MessageDTO} from "../model/message.dto";

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule
  ],
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  messages: MessageDTO[] = [];
  senderId: string = '';
  receiverId: string = '';
  ccId: string = '';
  content: string = '';

  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    private authService: AuthService
  ) {
    const userId = this.authService.getUserId();
    if (userId) {
      this.senderId = userId;
    } else {
      // Handle case where userId is null, for example, redirect to login
      alert('User not logged in');
    }
    this.receiverId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit() {
    this.loadInboxMessages();
  }

  loadInboxMessages(): void {
    this.messageService.getInboxMessages(this.receiverId).subscribe(messages => {
      this.messages = messages;
    });
  }

  sendMessage(): void {
    if (this.senderId) {
      const message: MessageDTO = {
        senderId: this.senderId,
        receiverId: this.receiverId,
        ccId: this.ccId,
        content: this.content,
        isRead: false
      };
      this.messageService.sendMessage(message).subscribe(sentMessage => {
        this.messages.push(sentMessage);
        this.content = '';
        alert('Message sent successfully');
      });
    } else {
      alert('Sender ID is not available');
    }
  }
}
