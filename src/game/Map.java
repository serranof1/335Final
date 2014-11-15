package game;

import java.util.LinkedList;

public class Map {

	private Tile[][] map = new Tile[30][30];
	protected LinkedList<Tile> resourceTiles = new LinkedList<Tile>();
	
	public Map() {
		
		for(int i = 0; i < map.length; i++){
			for(int j =0; j < map.length; j++){
				map[i][j] = new Tile(i , j);
			}
		}
				
		for(int i = 0; i < map.length - 1; i++){
			for(int j =0; j < map.length - 1; j++){
				map[i][j].setWest(map[((30 + (i - 1)) %30)][j]);
				map[i][j].setEast(map[((30 + (i + 1)) %30)][j]);
				map[i][j].setNorth(map[i][(30 + (i - 1)) %30]);
				map[i][j].setSouth(map[i][(30 + (i + 1)) %30]);
			}
		}
		
		map[14][14].setResource(true);
		resourceTiles.add(map[14][14]);
	}

	public String toString(){
		
		String result = "";
		
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map.length; j++){
				
				
				result += "[ "+map[i][j].toString() +" ]";
			}
			result += "\n";
		}
	return result;
	}

	public void findShortestPath(){
		
	}

	public Tile getTile(int i, int j) {
		return map[5][5];
	}
}
