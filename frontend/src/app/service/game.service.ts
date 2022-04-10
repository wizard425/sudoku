import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const URL = 'http://10.216.220.104:40000';
//const URL = 'http://kobcloud.com:40000';


@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) { }

  //Setzt nummber in der angegebenen Koordinate
  setNumber(gameID: string, x: number, y: number, value: number){
    return this.http.get(`${URL}/${gameID}/setnumber/${y}/${x}/${value}`);
  }

  //Rueckgabe ist int[][] mit neuem Sudoku
  createGame(): Observable<HttpResponse<number[][]>>{
    return this.http.get<number[][]>(`${URL}/create`,
      { observe: 'response'});
  }

  //Gibt aktuelle feld vom spiel zurück
  actualField(gameID: String){
    return this.http.get(`${URL}/${gameID}/field`);
  }

  //
  solveField(gameID: string): Observable<number[][]>{
    return this.http.get<number[][]>(`${URL}/${gameID}/solve`);
  }
}
