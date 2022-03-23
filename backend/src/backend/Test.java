package backend;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		Game t = new Game();

		/*int[][] te = t.createRandomField();
		 * 
		 */
		SudokuFunctions.visualizeInTerminal(t.solve());
	}

}
