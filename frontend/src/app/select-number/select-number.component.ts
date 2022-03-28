import { GameService } from './../service/game.service';
import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'su-select-number',
  templateUrl: './select-number.component.html',
  styleUrls: ['./select-number.component.scss'],
})
export class SelectNumberComponent implements OnInit {

  @Output() changeEvent: EventEmitter<any> = new EventEmitter<any>();
  error: HttpErrorResponse | null = null;

  constructor(
    public dialogRef: MatDialogRef<SelectNumberComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gs: GameService
  ) {}

  ngOnInit(): void {}

  addNumber(n: number) {
    let gameid = localStorage.getItem("Gameid");
    if(gameid){
      this.gs.setNumber(gameid, this.data.y, this.data.x, n).subscribe((res) => {
        this.dialogRef.close();
        this.changeEvent.emit(res);
      },(err) => {
        this.error = err
      });
    }
  }
}
