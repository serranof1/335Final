package game;

public class Map {

	private Terrain[][] map = new Terrain[30][30];
	
	public Map() {
		
		for(int i = 0; i < map.length; i++){
			for(int j =0; j < map.length; j++){
				map[i][j] = new Terrain(i , j);
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

	public Terrain getTerrain(int i, int j) {
		return map[5][5];
	}
}
