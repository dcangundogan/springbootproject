import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MessageDTO } from '../model/message.dto';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  sendMessage(message: MessageDTO): Observable<MessageDTO> {
    return this.http.post<MessageDTO>(`${this.apiUrl}/send`, message, { headers: this.getAuthHeaders() });
  }

  getInboxMessages(userId: string): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>(`${this.apiUrl}/received/${userId}`, { headers: this.getAuthHeaders() });
  }

  updateMessage(message: MessageDTO): Observable<MessageDTO> {
    return this.http.put<MessageDTO>(`${this.apiUrl}/${message.id}`, message, { headers: this.getAuthHeaders() });
  }

  getMessagesByParentId(parentId: string): Observable<MessageDTO[]> {
    return this.http.get<MessageDTO[]>(`${this.apiUrl}/thread/${parentId}`, { headers: this.getAuthHeaders() });
  }
}
