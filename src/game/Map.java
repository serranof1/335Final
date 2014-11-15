package game;

import java.util.LinkedList;

public class Map {

	private Tile[][] map = new Tile[10][10];
	protected LinkedList<Tile> resourceTiles = new LinkedList<Tile>();
	
	public Map() {
		
		for(int i = 0; i < map.length; i++){
			for(int j =0; j < map.length; j++){
				map[i][j] = new Tile(i , j);
			}
		}
				
		for(int i = 0; i < map.length - 1; i++){
			for(int j =0; j < map.length - 1; j++){
				map[i][j].setWest(map[((10 + (i - 1)) %10)][j]);
				map[i][j].setEast(map[((10 + (i + 1)) %10)][j]);
				map[i][j].setNorth(map[i][(10 + (i - 1)) %10]);
				map[i][j].setSouth(map[i][(10 + (i + 1)) %10]);
			}
		}
		
		map[5][5].setResource(true);
		resourceTiles.add(map[6][6]);
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
		return map[i][j];
	}
}
