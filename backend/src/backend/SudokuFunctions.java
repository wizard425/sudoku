package backend;

import java.util.ArrayList;
import java.util.Arrays;

public class SudokuFunctions {

	public static int[][] solvedField = new int[9][9];

	public static int fieldsToRemoveEasy = 50;
	public static int fieldsToRemoveMiddle = 60;
	public static int fieldsToRemoveHard = 70;

	public static int[][] getRandomField() {
		int[][] ret = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int place = (int) ((Math.random() * 9) + 1);
				boolean valid = true;
				// System.out.println(place);
				while (valid) {
					place = (int) ((Math.random() * 9) + 1);
					// System.out.println(place);

					// System.out.println(i + " " + j +" " + place + "isValid: "+ isValid(i, j,
					// place));
					if (SudokuFunctions.isValid(ret, i, j, place)) {
						ret[i][j] = place;
						valid = false;
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
		ArrayList<Integer> cluster = SudokuFunctions.getCluster(feld, y, x);

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

		for (int i = 0; i < cluster.size(); i++) {
			if (placed == cluster.get(i)) {
				return false;
			}
		}

		return ret;
	}

	public static ArrayList<Field> getCluster(Field[][] feld, int y, int x) {
		ArrayList<Field> ret = new ArrayList<Field>();

		int clusterRow = y / 3;
		int clusterColumn = x / 3;

		for (int i = 3 * clusterRow; i < 3 * clusterRow + 3; i++) {
			for (int j = 3 * clusterColumn; j < 3 * clusterRow + 3; j++) {
				ret.add(feld[i][j]);
			}
		}

		return ret;
	}

	public static ArrayList<Integer> getCluster(int[][] feld, int y, int x) {
		ArrayList<Integer> ret = new ArrayList<Integer>();

		int clusterRow = y / 3;
		int clusterColumn = x / 3;

		for (int i = 3 * clusterRow; i < 3 * clusterRow + 3; i++) {
			for (int j = 3 * clusterColumn; j < 3 * clusterColumn + 3; j++) {
				ret.add(feld[i][j]);
			}
		}

		return ret;
	}

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

	public static int[][] createFullValidField(int mode) {
		int[][] ret = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				ret[i][j] = 0;
			}
		}
		ret[4][4] = (int) (Math.random() * 9 + 1);

		for (int i = 0; i < 9; i++) {
			boolean valid = true;
			if (ret[i][4] == 0) {
				while (valid) {
					ret[i][4] = (int) (Math.random() * 9 + 1);
					if (SudokuFunctions.isFieldValid(ret)) {
						valid = false;
					}
				}
			}
		}

		for (int i = 0; i < 9; i++) {
			boolean valid = true;
			if (ret[4][i] == 0) {
				while (valid) {
					ret[4][i] = (int) (Math.random() * 9 + 1);
					if (SudokuFunctions.isFieldValid(ret)) {
						valid = false;
					}
				}
			}
		}
		if (mode == 1)
			mode = SudokuFunctions.fieldsToRemoveEasy;
		if (mode == 2)
			mode = SudokuFunctions.fieldsToRemoveMiddle;
		if (mode == 3)
			mode = SudokuFunctions.fieldsToRemoveHard;

		ArrayList<int[]> toRemove = SudokuFunctions.getFieldsToRemove(mode);

		SudokuFunctions.solveBoard(ret);
		SudokuFunctions.visualizeInTerminal(ret);
		
		for( int i = 0; i < toRemove.size(); i++) {
			int[] rem = toRemove.get(i);
			System.out.println(i);
			solvedField[rem[0] - 1][rem[1] - 1] = 0;
		}
		return solvedField;
	}

	private static ArrayList<int[]> getFieldsToRemove(int fieldsToRemove) {
		ArrayList<int[]> ret = new ArrayList<int[]>();
		for (int i = 0; i < fieldsToRemove; i++) {
			boolean valid = true;
			while (valid) {
				int x = (int) (Math.random() * +9 + 1);
				int y = (int) (Math.random() * +9 + 1);
				int[] check = new int[] { y, x };
				System.out.println(Arrays.toString(check));
				if (!ret.contains(check)) {
					valid = false;
					ret.add(check);
				}
			}
		}
		Arrays.deepToString(ret.toArray());
		return ret;
	}

	public int[][] solve(int[][] fieldToSolve) {
		int[][] ret = new int[9][9];
		solveBoard(fieldToSolve);
		return solvedField;
	}

	public static boolean solveBoard(int[][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == 0) {
					for (int x = 1; x < 10; x++) {
						board[i][j] = x;
						if (isFieldValid(board) && solveBoard(board)) {
							return true;
						}
						board[i][j] = 0;
					}
					return false;
				}
			}
		}
		solvedField = board;
		return true;
	}

	public static boolean isFieldValid(int[][] board) {
		boolean ret = true;
		for (int i = 0; i < 9; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			ArrayList<Integer> tempCol = new ArrayList<Integer>();
			for (int j = 0; j < 9; j++) {
				if (temp.contains(board[i][j])) {
					return false;
				}
				if (tempCol.contains(board[j][i])) {
					return false;
				}
				if (board[i][j] != 0)
					temp.add(board[i][j]);
				if (board[j][i] != 0)
					tempCol.add(board[j][i]);
				ArrayList<Integer> cluster = SudokuFunctions.getCluster(board, i, j);
				int count = 0;
				for (int a = 0; a < cluster.size(); a++) {
					if (cluster.get(a) == board[i][j] && cluster.get(a) != 0)
						if (count == 0)
							count++;
						else {
							return false;
						}
				}

			}

		}

		return ret;
	}

}
