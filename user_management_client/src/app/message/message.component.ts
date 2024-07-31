import {Component, OnInit} from '@angular/core';
import {MessageDTO, MessageService} from "../services/message.service";
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-message',
  standalone: true,
  templateUrl: './message.component.html',
  imports: [
    FormsModule,
    NgForOf
  ],
  styleUrl: './message.component.css'
})
export class MessageComponent implements OnInit{
  messages: MessageDTO[] = [];
  senderId: string;
  receiverId: string;
  content: string;
  isRead:  boolean;
  constructor(private messageService:MessageService) {
  }
  ngOnInit() {
    this.loadMessages();
  }

  loadMessages():void{
    this.messageService.getMessagesBetweenUsers(this.senderId,this.receiverId).subscribe(messages=>{
      this.messages=messages;
    })
  }
  sendMessage(): void {
    const message: MessageDTO = { senderId: this.senderId, receiverId: this.receiverId, content: this.content };
    this.messageService.sendMessage(message).subscribe(sentMessage => {
      this.messages.push(sentMessage);
      this.content = '';
    });

}}
