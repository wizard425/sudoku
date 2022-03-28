package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class GameHandler {

	public static GameHandler ghInstance;
	
	private HashMap<String, Game> games = new HashMap<String, Game>();
	
	
	public static GameHandler getInstance() {
		if(ghInstance == null)
			ghInstance = new GameHandler();
		
		return ghInstance;
	}
	
	
	public Game getGame(String key) {
		return this.games.get(key);
	}
	
	public Game createNewGame(String key, int difficulty) {
		Game newGame = new Game();
		int[][] temp = SudokuFunctions.createFullValidField(difficulty);
		
		newGame.gamefield = temp;
		
		games.put(key, newGame);
		
		return newGame;
	}
	
	public ArrayList<Game> getAllGames() {
		ArrayList<Game> ret = new ArrayList<Game>();
		for (String key: games.keySet()) {
			ret.add(this.games.get(key));
		}
		return ret;
	}
}
