import { GameService } from './../service/game.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'su-select-number',
  templateUrl: './select-number.component.html',
  styleUrls: ['./select-number.component.scss'],
})
export class SelectNumberComponent implements OnInit {
  //TODO: has to be UUID
  gameID!: string;

  selected!: number;

  constructor(
    public dialogRef: MatDialogRef<SelectNumberComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private gs: GameService
  ) {}

  ngOnInit(): void {}

  addNumber(n: number) {
    //TODO: fill the subscribe
    this.gs.setNumber(this.gameID, this.data.y, this.data.x, n).subscribe();
  }
}
