import {Component,Input,Output,EventEmitter} from "@angular/core";



@Component({
  selector:'app-pagination',
  templateUrl:'./pagination.component.html',
  styleUrls:['./pagination.component.html'],
  standalone:true
})
export class PaginationComponent{
  @Input() currentPage: number | undefined;
  @Input() itemsPerPage: number | undefined;
  @Input() totalItems: number | undefined;
  @Input() pageChanged:EventEmitter<number>= new EventEmitter<number>();



  get totalPages(): number {
    //@ts-ignores
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.pageChanged.emit(page);
    }
}}
