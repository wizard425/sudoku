package backend;

import java.util.ArrayList;

public class SudokuFunctions {

	public static int[][] getRandomField() {
		int[][] ret = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int place = (int) ((Math.random() * 9) + 1);
				boolean valid = true;
				// System.out.println(place);
				while (valid) {
					place = (int) ((Math.random() * 9) + 1);
					//System.out.println(place);

					// System.out.println(i + " " + j +" " + place + "isValid: "+ isValid(i, j,
					// place));
					if (SudokuFunctions.isValid(ret, i, j, place)) {
						ret[i][j] = place;
						valid = false;
						System.out.println("in isvalid" + i + " +++++ " + j + " +++++ " + place);
						SudokuFunctions.visualizeInTerminal(ret);
						if (j == 8)
							valid = false;
					} else {
						place = (int) ((Math.random() * 9) + 1);
					}
				}
			}
		}
		return ret;
	}

	public static boolean isValid(Field[][] feld, int y, int x, int placed) {
		boolean ret = true;
		if (feld[y][x].value != 0)
			return false;
		ArrayList<Field> cluster = SudokuFunctions.getCluster(feld, y, x);

		for (int i = 0; i < 9; i++) {
			// check row compatibily
			if (feld[y][i].value == placed) {
				return false;
			}

			// check column compatibily
			if (feld[i][x].value == placed) {
				return false;
			}
		}
		for (int i = 0; i < cluster.size(); i++) {
			if (placed == cluster.get(i).value) {
				return false;
			}
		}
		return ret;
	}

	public static boolean isValid(int[][] feld, int y, int x, int placed) {
		boolean ret = true;
		if (feld[y][x] != 0)
			return false;
		// ArrayList<Field> cluster = SudokuFunctions.getCluster(feld, y, x);

		for (int i = 0; i < 9; i++) {
			// check row compatibily
			if (feld[y][i] == placed) {
				return false;
			}

			// check column compatibily
			if (feld[i][x] == placed) {
				return false;
			}
		}
		/*
		 * for (int i = 0; i < cluster.size(); i++) { if (placed ==
		 * cluster.get(i).value) { return false; } }
		 */
		return ret;
	}

	public static ArrayList<Field> getCluster(Field[][] feld, int y, int x) {
		ArrayList<Field> ret = new ArrayList<Field>();

		int clusterRow = y / 3;
		int clusterColumn = x / 3;

		for (int i = 3 * clusterRow; i < 3 * clusterRow + 3; i++) {
			for (int j = 3 * clusterColumn; j < 3 * clusterRow + 3; j++) {
				System.out.println(feld[i][j].value);
				ret.add(feld[i][j]);
			}
		}

		return ret;
	}

	/*
	 * public static ArrayList<Field> getCluster(Field[][] feld, int y, int x) {
	 * ArrayList<Field> ret = new ArrayList<Field>();
	 * 
	 * int clusterRow = y / 3; int clusterColumn = x / 3;
	 * 
	 * for (int i = 3 * clusterRow; i < 3 * clusterRow + 3; i++) { for (int j = 3 *
	 * clusterColumn; j < 3 * clusterRow + 3; j++) {
	 * System.out.println(feld[i][j].value); ret.add(feld[i][j]); } }
	 * 
	 * return ret; }
	 */

	public static int[][] getGameInInt(Field[][] feld) {
		int[][] ret = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				ret[i][j] = feld[i][j].value;
			}
		}
		return ret;
	}

	public static void visualizeInTerminal(int[][] printt) {
		System.out.println("\n#################################");
		for (int i = 0; i < 9; i++) {
			System.out.println();
			for (int j = 0; j < 9; j++) {
				System.out.print(printt[i][j] + " | ");
			}
		}
		System.out.println("\n#################################");
	}

}
