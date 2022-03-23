package backend;

import java.util.ArrayList;

public class Game {
	
	Field[][] gamefield = new Field[9][9];
	
	int[][] temp= new int[][] {
	                      {0,3,0,7,0,4,8,0,5},
	                      {0,7,0,5,0,9,0,4,0},
	                      {0,0,0,0,0,6,0,0,1},
	                      {8,1,0,6,5,0,0,7,4},
	                      {7,6,0,0,4,0,1,0,0},
	                      {0,5,0,0,7,8,0,0,0},
	                      {4,0,0,2,0,1,5,8,0},
	                      {0,0,6,0,0,5,0,0,0},
	                      {5,0,3,0,6,7,0,1,2}
	};
			
	
	
	Game(){
		
		for( int i = 0; i < 9; i++) {
			for( int j = 0; j < 9; j++) {
				this.gamefield[i][j] = new Field();
				this.gamefield[i][j].x = j;
				this.gamefield[i][j].y = i;
				this.gamefield[i][j].value = temp[i][j];
			}
		}
		
		
		
	}
	
	public boolean makeMove(int y , int x, int placed) {
		boolean ret = false;
		//int[][] ret = new int[9][9];
		
		if(isValid(y, x, placed)) {
			this.gamefield[y][x].value = placed;
			ret = true;
		}
		return ret;
	}
	
	
	public boolean isValid(int y, int x , int placed){
		boolean ret = true;
		if(this.gamefield[y][x].value != 0)
			return false;
		
		ArrayList<Field> cluster= this.getCluster(y, x);
		
		for( int i = 0; i < 9; i++) {
			// check row compatibily
			if(this.gamefield[y][i].value == placed) {
				return false;
			}
			
			// check column compatibily
			if(this.gamefield[i][x].value == placed) {
				return false;
			}
		}
		
		for( int i = 0; i < cluster.size(); i++) {
			if(placed == cluster.get(i).value) {
				return false;
			}
		}
		
		return ret;
	}
	
	public ArrayList<Field> getCluster(int y, int x){
		ArrayList<Field> ret = new ArrayList<Field>();
		
		int clusterRow = y/3;
		int clusterColumn = x/3;
		
		for( int i = 3*clusterRow; i < 3*clusterRow + 3 ; i++) {
			for( int j = 3*clusterColumn;j < 3*clusterRow + 3 ; j++) {
				System.out.println(this.gamefield[i][j].value);
				ret.add(this.gamefield[i][j]);
			}
		}
		
		return ret;
	}
	
	public int[][] getFieldInInts(){
		int[][] ret = new int[9][9];
		
		for( int i = 0; i < 9; i++) {
			for( int j = 0; j < 9; j++) {
				ret[i][j] = this.gamefield[i][j].value;
			}
		}
		return ret;
	}
	
	public int[][] createRandomField(){
		int[][] ret = new int[9][9];
		
		for( int i = 0; i < 9; i++) {
			for( int j = 0; j < 9; j++) {
				int place = (int) ((Math.random()*9) + 1);
				boolean valid = true;
				//System.out.println(place);
				while(valid) {
					place = (int) ((Math.random()*9) + 1);

					//System.out.println(i + "   " + j +"   " + place + "isValid:   "+ isValid(i, j, place));
					if(this.makeMove(i, j, place)) {
						
						System.out.println("in isvalid");
						valid = false;
					}else {
						place = (int) ((Math.random()*9) + 1);					}
				}
			}
		}
		
		return ret;
	}
	
	public void visualizeInTerminal() {
		for( int i = 0; i < 9; i++) {
			System.out.println();
			for( int j = 0; j < 9; j++) {
				System.out.print(this.gamefield[i][j].value + " | ");
			}
		}
	}
	
	public void visualizeInTerminal(int[][] printt) {
		for( int i = 0; i < 9; i++) {
			System.out.println();
			for( int j = 0; j < 9; j++) {
				System.out.print(printt[i][j] + " | ");
			}
		}
	}
	
}