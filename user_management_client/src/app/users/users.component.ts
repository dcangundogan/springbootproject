import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import {MatSort, MatSortHeader, Sort} from '@angular/material/sort';
import { UserService } from '../services/user.service';
import { User } from '../model/user.model';
import { Router } from '@angular/router';
import { MessageService } from '../services/message.service';
import {MessageDTO} from "../model/message.dto";
import { AuthService } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import {CurrencyPipe, DatePipe, NgForOf, NgIf, SlicePipe} from '@angular/common';
import { MatButton } from '@angular/material/button';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatTable, MatColumnDef, MatHeaderCell, MatHeaderCellDef, MatCell, MatCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef } from '@angular/material/table';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatAccordion, MatExpansionPanel, MatExpansionPanelDescription, MatExpansionPanelHeader, MatExpansionPanelTitle } from '@angular/material/expansion';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  imports: [
    FormsModule,
    CurrencyPipe,
    DatePipe,
    MatButton,
    MatCard,
    MatCardContent,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRowDef,
    MatRow,
    MatPaginator,
    MatSort,
    MatFormField,
    MatInput,
    MatLabel,
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelTitle,
    MatExpansionPanelDescription,
    MatExpansionPanelHeader,
    NgIf,
    SlicePipe,
    NgForOf,
    MatSortHeader
  ],
  standalone: true
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  totalUsers: number = 0;
  pageSize: number = 10;
  currentPage: number = 0;
  sortBy: string = 'name';
  sortDirection: string = 'asc';

  displayedColumns: string[] = ['name', 'surname', 'email', 'identityNumber', 'birthDate', 'salary', 'actions'];

  searchTerms: any = {
    name: '',
    surname: '',
    email: '',
    identityNumber: '',
    birthDate: '',
    salary: ''
  };

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  showMessagePanel: boolean = false;
  showInbox: boolean = false;
  receiverId: string = '';
  ccId: string = '';
  content: string = '';
  senderId: string | null = '';
  inboxMessages: MessageDTO[] = [];
  threadMessages: { [parentId: string]: MessageDTO[] } = {};
  hasNewMessages: boolean = false;
  parentId: string | null = null;

  constructor(
    private userService: UserService,
    private router: Router,
    private messageService: MessageService,
    private authService: AuthService
  ) {
    this.authService.fetchUserDetails().subscribe(user => {
      this.senderId = user?.id || null;
      if (!this.senderId) {
        alert('User not logged in');
        this.router.navigate(['/login']);
      } else {
        this.loadInboxMessages();
      }
    });
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers(this.currentPage, this.pageSize, this.searchTerms, this.sortBy, this.sortDirection).subscribe(
      data => {
        this.users = data.content;
        this.totalUsers = data.totalElements;
      },
      error => {
        console.error('Error fetching users', error);
      }
    );
  }

  loadInboxMessages(): void {
    if (this.senderId) {
      this.messageService.getInboxMessages(this.senderId).subscribe(messages => {
        this.inboxMessages = messages.filter(msg => !msg.parentId); // Load only root messages
        this.hasNewMessages = this.inboxMessages.some(message => !message.isRead);
      });
    }
  }

  loadThreadMessages(parentId: string): void {
    if (!this.threadMessages[parentId]) {
      this.messageService.getMessagesByParentId(parentId).subscribe(messages => {
        this.threadMessages[parentId] = messages;
      });
    }
  }

  markMessageAsRead(message: MessageDTO): void {
    message.isRead = true;
    this.messageService.updateMessage(message).subscribe(() => {
      this.hasNewMessages = this.inboxMessages.some(msg => !msg.isRead);
    });
  }

  onSearchChange(): void {
    this.currentPage = 0; // Reset to first page on search
    this.loadUsers();
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadUsers();
  }

  onSortChange(sort: Sort): void {
    this.sortBy = sort.active;
    this.sortDirection = sort.direction;
    this.loadUsers();
  }

  navigateToRoles(): void {
    this.router.navigate(['/roles']);
  }

  openMessagePanel(userId: string): void {
    this.receiverId = userId;
    this.parentId = null; // Reset parentId for a new message
    this.showMessagePanel = true;
  }

  closeMessagePanel(): void {
    this.showMessagePanel = false;
  }

  toggleInbox(): void {
    this.showInbox = !this.showInbox;
    if (this.showInbox) {
      this.loadInboxMessages();
    }
  }

  sendMessage(): void {
    if (this.senderId) {
      const message: MessageDTO = {
        senderId: this.senderId,
        receiverId: this.receiverId,
        ccId: this.ccId,
        content: this.content,
        isRead: false,
        parentId: this.parentId // Use parentId set for reply, or null for new message
      };
      this.messageService.sendMessage(message).subscribe(
        sentMessage => {
          this.showMessagePanel = false;
          this.content = '';
          alert('Message sent successfully');
          this.loadInboxMessages(); // Reload messages to include new message
        },
        error => {
          console.error('Error sending message:', error);
          alert('Error sending message');
        }
      );
    } else {
      alert('Sender ID is not available');
    }
  }

  replyMessage(receiverId: string, parentId: string | undefined): void {
    if (parentId) {
      this.receiverId = receiverId;
      this.parentId = parentId; // Set the parentId for the reply
      this.content = '';
      this.showMessagePanel = true;
      this.loadThreadMessages(parentId);
    } else {
      console.error('Parent ID is undefined');
      alert('Error: Parent message not found.');
    }
  }

  getUserFullName(userId: string): string {
    const user = this.users.find(u => u.id === userId);
    return user ? `${user.name} ${user.surname}` : 'Unknown User';
  }

  isParentIdDefined(messageId: string | undefined): boolean {
    return messageId !== undefined;
  }
}
