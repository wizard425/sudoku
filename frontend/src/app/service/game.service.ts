import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const URL = 'http://';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  //Setzt nummber in der angegebenen Koordinate
  setNumber(gameID: String, y: number, x: number, value: number){
    return this.http.get(`${URL}/${gameID}/setnumber/${y}/${x}/${value}`);
  }

  //Rueckgabe ist int[][] mit neuem Sudoku
  createGame(){
    return this.http.get(`${URL}/create`);
  }

  //Gibt aktuelle feld vom spiel zurück
  actualField(gameID: String){
    return this.http.get(`${URL}/${gameID}/field`);
  }
}
