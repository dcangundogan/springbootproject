import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


export interface MessageDTO {
  id?: string;
  senderId: string;
  receiverId: string;
  content: string;
  timestamp?: string;
}

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) { }

  sendMessage(message: MessageDTO): Observable<MessageDTO> {
    return this.http.post<MessageDTO>(`${this.apiUrl}/send`, message);
  }

  getMessagesBetweenUsers(senderId: string, receiverId: string): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>(`${this.apiUrl}/between`, { params: { senderId, receiverId } });
  }

  getMessagesBySender(senderId: string): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>(`${this.apiUrl}/sent`, { params: { senderId } });
  }

  getMessagesByReceiver(receiverId: string): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>(`${this.apiUrl}/received`, { params: { receiverId } });
  }
}
