import { GameService } from './../service/game.service';
import { SelectNumberComponent } from './../select-number/select-number.component';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'su-field',
  templateUrl: './field.component.html',
  styleUrls: ['./field.component.scss'],
})
export class FieldComponent implements OnInit {
  hoverMouse: boolean;

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
  constructor(private dialog: MatDialog, private gs: GameService) {
    this.hoverMouse = false;
  }

  ngOnInit(): void {
    this.gs.createGame().subscribe((res) => {
      if (res.body != null) {
        this.temp = Array.from(res.body);
        let gameid = res.headers.get('Gameid');
        if (gameid) localStorage.setItem('Gameid', gameid);
      }
    });
  }

  //Öffnet Dialog zur Zahleneingabe
  openDialog(yNumber: number, xNumber: number) {
    let dialogRef = this.dialog.open(SelectNumberComponent, {
      data: {
        x: xNumber,
        y: yNumber,
      },
    });
    dialogRef.componentInstance.changeEvent.subscribe(
      (res) => (this.temp = res)
    );
  }

  newGame() {
    this.gs.createGame().subscribe((res) => {
      if (res.body != null) {
        this.temp = Array.from(res.body);
        let gameid = res.headers.get('Gameid');
        if (gameid) localStorage.setItem('Gameid', gameid);
      }
    });
  }

  //Löst das Feld
  solveField() {
    let gameid = localStorage.getItem('Gameid');
    if (gameid)
      this.gs.solveField(gameid).subscribe((res) => (this.temp = res));
  }
}
