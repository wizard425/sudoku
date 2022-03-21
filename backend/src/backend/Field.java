package backend;

public class Field {

	int y;
	int x;
	
	int value;
	
	int [] possibleValues = new int[9];
	
	Field (){
		
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int[] getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(int[] possibleValues) {
		this.possibleValues = possibleValues;
	}
	
	

	
}
