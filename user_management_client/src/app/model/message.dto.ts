export interface MessageDTO {
  id?: string;
  senderId: string;
  receiverId: string;
  ccId?: string;
  content: string;
  timestamp?: string;
  isRead: boolean;
}
