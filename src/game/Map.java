package game;

import java.util.Random;

import model.Building;
import resources.*;
import tiles.*;

public class Map {
	private Tile[][] map;
	private int size, n;
	private long seed;
	private Random rand;
	
	private float mountainThreshold = .8f;
	private float groundThreshold = .45f;
	private float sandThreshold = .3f;
	//I think, for a map of any particular size, the linkedlist of resources becomes difficult to use
	//so I did not implement it here. We may need to figure this out.
	
	public Map(int n, long seed) {
		this.n = n;
		size = (int) Math.pow(2, n) + 1;
		this.seed = seed;
		rand = new Random(seed);
		map = buildNodeMap();
	}
	
	public Map(int n) {
		this.n = n;
		size = (int) Math.pow(2, n) + 1;
		rand = new Random();
		seed = rand.nextLong();
		rand = new Random(seed);
		map = buildNodeMap();
	}
	
	private float[][] buildFloatMap() {
		//int size = (int) Math.pow(2, n) + 1; //map is 1+2^n by 1+2^n
		float[][] map = new float[size][size];
		
		//initialize corners of the map
		//we can initialize more points to affect the generation; this will need experimentation
		/*
		for (int i = 0; i < size; i += size) {
			for (int j = 0; j < size; j += size) {
				map[i][j] = rand.nextFloat();
			}
		}*/
		
		//experimentation with initializing more points. Uncomment the above to default to the corners
		for (int i = 0; i < size; i += size / 4) {
			for (int j = 0; j < size; j += size / 4) {
				map[i][j] = rand.nextFloat();
			}
		}
		
		//initialize each midpoint
		
		/*
		 * When we set the step size, this needs to be one factor of 2 smaller than the points 
		 * initialized, otherwise it is moot. That is, the step must be "in-between" the points,
		 * if every n points are initialized (0, n, 2n, ...) we have to draw at the stepsize of n/2
		 * (n/2, 3n/2, 5n/2, ...)
		 */
		//int step = (int) Math.pow(2, n) / 2;
		int step = (int) Math.pow(2, n) / 8; 
		int count = 1;
		float smoothness = 1;
		float sum;
		while (step > 0) {
			for (int x = step; x < size; x += 2 * step) {
				for (int y = step; y < size; y+= 2 * step) {
					sum = map[x - step][y - step] +
							map[x - step][y + step] +
							map[x + step][y - step] +
							map[x + step][y + step];
					map[x][y] = (float) (sum / 4 + (rand.nextFloat() - .5) * smoothness);
				}
			}
			
			count = 1;
			for (int x = 0; x < size; x += step) {
				for (int y = 0; y < size; y += step) {
					if (count % 2 == 0) {
						if ((x / step) % 2 == 1) {
							sum = map[x - step][y] + map[x + step][y];
							map[x][y] = (float) (sum / 2 + (rand.nextFloat() - .5) * smoothness);
						} else {
							sum = map[x][y - step] + map[x][y + step];
							map[x][y] = (float) (sum / 2 + (rand.nextFloat() - .5) * smoothness);
						}
					}
					count++;
				}
			}
			smoothness /= 2;
			step /= 2;
		}/*
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				System.out.print("[" + map[x][y] + "]");
			}
			System.out.println();
		}*/
		return map;
	}
	
	private float[][] normalizeMap() {
		float[][] normalizedMap = new float[size][size];
		float[][] map = buildFloatMap();
		float max = 0;
		float min = 0;
		for (float[] row : map) {
			for (float cell : row) {
				if (cell < min) {
					min = cell;
				}
				if (cell > max) {
					max = cell;
				}
			}
		}
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				normalizedMap[x][y] = (map[x][y] - min) / (max - min);
			}
		}
		return normalizedMap;
	}

	private Tile[][] buildTileMap() {
		Tile[][] tileMap = new Tile[size][size];
		float[][] floatMap = normalizeMap();
		GroundTile plain = new GroundTile(GroundEnum.PLAIN);
		GroundTile mountain = new GroundTile(GroundEnum.MOUNTAIN);
		GroundTile ocean = new GroundTile(GroundEnum.OCEAN);
		GroundTile sand = new GroundTile(GroundEnum.SAND);
		
		ResourceTile noResource = new ResourceTile(ResourceEnum.NOTHING);
		BuildingTile noBuilding = new BuildingTile(BuildingEnum.NOTHING);
		WeatherTile noWeather = new WeatherTile(WeatherEnum.NOTHING);
		
		ResourceTile methane = new ResourceTile(ResourceEnum.METHANE);
		ResourceTile carbon = new ResourceTile(ResourceEnum.CARBON);
		ResourceTile iron = new ResourceTile(ResourceEnum.IRON);
		
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//we may need to switch i and j; I can never remember if the outer loop is the row or the column
				if (floatMap[i][j] > mountainThreshold) {
					tileMap[i][j] = new Tile(mountain, iron, noBuilding, noWeather);
				} else if (floatMap[i][j] > groundThreshold) {
					tileMap[i][j] = new Tile(plain, noResource, noBuilding, noWeather);
				} else if (floatMap[i][j] > sandThreshold) {
					tileMap[i][j] = new Tile(sand, carbon, noBuilding, noWeather);
				} else {
					tileMap[i][j] = new Tile(ocean, methane, noBuilding, noWeather);
				}
				tileMap[i][j].setX(i);
				tileMap[i][j].setY(j);
			}
		}
		return tileMap;
	}
	
	
	private Tile[][] buildNodeMap() {
		Tile[][] map = buildTileMap();
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				map[i][j].setWest(map[i][(size + (j - 1)) % size ]);
				map[i][j].setEast(map[i][(size + (j + 1)) % size ]);
				map[i][j].setNorth(map[(size + (i - 1)) % size ][j]);
				map[i][j].setSouth(map[(size + (i + 1)) % size ][j]);
			}
		}
		return map;
	}
	private String drawTextForm() {
		String s = new String();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				s += "[" + map[i][j].drawTextForm() + "]";
			}
			s += "\n";
		}
		return s;
	}
	
	public int getSize(){
		return size;
	}
	public String toString() {
		String s = new String();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				s += "[" + map[i][j].drawTextForm() + "]";
			}
			s += "\n";
		}
		return s;
	}
	
	public Tile getTile(int i, int j) {
		return map[i][j];
	}
	
	public void setTile(Tile t, int x, int y) {
		map[x][y] = t;
	}
	
	public void build(Building b) {
		//These allows us to find the tile in our array for the building, ie, the top left corner of it
		int x = b.getLocation().x;
		int y = b.getLocation().y;
		Tile temp = null;
		
		//Defaulted to true for testing purposes
		if(true){
//		if (b.canBuild(map[x][y])) {
			/*
			 * Just like in the loop in Building, where it checks if it can build,
			 * this loop sets each tile to have the appropriate type of building.
			 * 
			 * Note: this required a method, getTypeOfBuilding() to be added to the
			 * abstract building class. Each building needs to hold the appropriate 
			 * enum in it. This will allow us to communicate the type of the building 
			 * to the map, when we are building.
			 *
			 *for (int i = 0; i < b.getWidth(); i++) {
				temp = map[x][y].getSouth();
				for (int j = 0; j < b.getHeight(); j++) {
					
					map[x][y].setBuilding(new BuildingTile(b.getTypeOfBuilding()));
					map[x][y] = map[x][y].getEast();
				}
				map[x][y] = temp;
			}*/
		//Fixed issue with not drawing upper left corner, not sure if the code above was for efficiency. Revision needed.
			for(int i = x; i < b.getWidth()+x;i++){
				for (int j = y; j < b.getHeight()+y; j++) {
					map[i][j].setBuilding(new BuildingTile(b.getTypeOfBuilding()));
				}
				
			}
		}
	}
}
