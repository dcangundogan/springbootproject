import { Component, Input, Output, EventEmitter } from "@angular/core";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css'],
  standalone: true
})
export class PaginationComponent {
  @Input() currentPage: number = 0; // Default to the first page
  @Input() itemsPerPage: number = 10; // Default to 10 items per page
  @Input() totalItems: number = 0; // Default to 0 total items
  @Output() pageChanged: EventEmitter<number> = new EventEmitter<number>();

  get totalPages(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  changePage(page: number): void {
    if (page >= 0 && page < this.totalPages) { // Pages are zero-indexed
      this.currentPage = page;
      this.pageChanged.emit(page);
    }
  }
}
