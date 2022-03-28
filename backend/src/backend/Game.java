package backend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Game {

	private static Game game;

	
	int[][] gamefield = new int[9][9];

	int[][] solvedField = new int[9][9];
	
	int[][] template = new int[][] { 
		{ 0, 0, 5, 3, 0, 0, 0, 0, 0 },
		{ 8, 0, 0, 0, 0, 0, 0, 2, 0 },
		{ 0, 7, 0, 0, 1, 0, 5, 0, 0 },
		{ 4, 0, 0, 0, 0, 5, 3, 0, 0 },
		{ 0, 1, 0, 0, 7, 0, 0, 0, 6 },
		{ 0, 0, 3, 2, 0, 0, 0, 8, 0 },
		{ 0, 6, 0, 5, 0, 0, 0, 0, 9 },
		{ 0, 0, 4, 0, 0, 0, 0, 3, 0 },
		{ 0, 0, 0, 0, 0, 9, 7, 0, 0 }
		};

	Game() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.gamefield[i][j] = template[i][j];
			}
		}
	}

	public static Game getInstance() {
		if (game == null) {
			game = new Game();
		}
		return game;
	}

	public boolean makeMove(int y, int x, int placed) {
		boolean ret = false;
		// int[][] ret = new int[9][9];

		if (isValid(y, x, placed)) {
			this.gamefield[y][x] = placed;
			ret = true;
		}
		return ret;
	}

	public boolean resetGame() {
		boolean ret = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.gamefield[i][j] = template[i][j];
				ret = true;
			}
		}
		return ret;
	}

	public boolean isValid(int y, int x, int placed) {
		boolean ret = true;
		if (this.gamefield[y][x] != 0)
			return false;

		ArrayList<int[]> cluster = this.getCluster(y, x);

		for (int i = 0; i < 9; i++) {
			// check row compatibly
			if (this.gamefield[y][i] == placed) {
				return false;
			}

			// check column compatibly
			if (this.gamefield[i][x] == placed) {
				return false;
			}
		}

		for (int i = 0; i < cluster.size(); i++) {
			if (placed == cluster.get(i)[2]) {
				return false;
			}
		}

		return ret;
	}

	public ArrayList<int[]> getCluster(int y, int x) {
		ArrayList<int[]> ret = new ArrayList<int[]>();

		int clusterRow = y / 3;
		int clusterColumn = x / 3;

		for (int i = 3 * clusterRow; i < 3 * clusterRow + 3; i++) {
			for (int j = 3 * clusterColumn; j < 3 * clusterRow + 3; j++) {
				ret.add(new int[] {i, j});
			}
		}

		return ret;
	}

	public int[][] getFieldInInts() {
		int[][] ret = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				ret[i][j] = this.gamefield[i][j];
			}
		}
		return ret;
	}

	public boolean isFieldValid(int[][] board) {
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
	
	public int[][] solve(){
		solveBoard(this.template);
		return this.solvedField;
	}

	public boolean solveBoard(int[][] board) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == 0) {
					for (int x = 1; x < 10; x++) {
						board[i][j] = x;
						if (this.isFieldValid(board) && solveBoard(board)) {
							return true;
						}
						board[i][j] = 0;
					}
					return false;
				}
			}
		}
		this.solvedField = board;
		return true;
	}

	public void visualizeInTerminal() {
		for (int i = 0; i < 9; i++) {
			System.out.println();
			for (int j = 0; j < 9; j++) {
				System.out.print(this.gamefield[i][j] + " | ");
			}
		}
	}

}