import { GameService } from './../service/game.service';
import { SelectNumberComponent } from './../select-number/select-number.component';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'su-field',
  templateUrl: './field.component.html',
  styleUrls: ['./field.component.scss']
})
export class FieldComponent implements OnInit {


  temp: number[][] = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
  ];

  //Test Comment
  constructor(private dialog: MatDialog, private gs: GameService) { }

  ngOnInit(): void {
    //TODO: Initial game
    this.gs.createGame().subscribe();
  }

  openDialog(xNumber: number, yNumber: number){
    let dialogRef = this.dialog.open(SelectNumberComponent, {
      data: {
        x: xNumber,
        y: yNumber,
      }
    });
  }

}
