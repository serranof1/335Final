package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import pathfinding.AStarPathFinder;
import pathfinding.Mover;
import pathfinding.PathFinder;
import model.Items;
import tiles.BuildingEnum;
import tiles.BuildingTile;
import tiles.GroundEnum;
import tiles.GroundTile;
import tiles.ResourceEnum;
import tiles.ResourceTile;
import tiles.Tile;
import tiles.WeatherEnum;
import tiles.WeatherTile;
import buildings.Building;
/**
 * The Map represents the 2D array on which the game is actually played. It holds each {@link Tile}, as
 * well as performs pathfinding.
 * @author Team Rosetta
 *
 */
public class Map {
	//good seed; lots of plains for troubleshooting: 7959250223445097006
	private Tile[][] map;
	private int size, n;
	private long seed;
	private Random rand;
	
	private float mountainThreshold = .85f;
	private float groundThreshold = .4f;
	private float sandThreshold = .3f;
	
	private AStarPathFinder finder;
	
	private BufferedImage droneImage;
	//I think, for a map of any particular size, the linkedlist of resources becomes difficult to use
	//so I did not implement it here. We may need to figure this out.
	
	private int numOfTerraformedTiles;
	
	private LinkedList<Building> allBuildings;
	
	/**
	 * 
	 * @param n - This is the "size" of the map generated using the diamond-square algorithm. Specifically,
	 * diamond-square is used to fractally (or more iteratively) generate a map of size 2^n+1 by 2^n+1.
	 * @param seed - A long used to seed the random number generator, should that be desired.
	 */
	public Map(int n, long seed) {
		try {
			droneImage = ImageIO.read(new File("images/drone.png"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No drone image");
		}
		this.n = n;
		size = (int) Math.pow(2, n) + 1;
		this.seed = seed;
		rand = new Random(seed);
		map = buildNodeMap();
		finder = new AStarPathFinder(this, 100, false);
		numOfTerraformedTiles = 0;
		allBuildings = new LinkedList<Building>();
	}
	/**
	 * 
	 * @param n - The size of the map.
	 */
	public Map(int n) {
		try {
			droneImage = ImageIO.read(new File("images/drone.png"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No drone image");
		}
		this.n = n;
		size = (int) Math.pow(2, n) + 1;
		rand = new Random();
		seed = rand.nextLong();
		rand = new Random(seed);
		System.out.println("Your seed is: " + seed);
		map = buildNodeMap();
		finder = new AStarPathFinder(this, 100, false);
		numOfTerraformedTiles = 0;
		allBuildings = new LinkedList<Building>();
	}
	/**
	 * This performs the first portion of the diamond-square algorithm, in which a 2D array of floats
	 * is generated.
	 * @return float[][]
	 */
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
	
	/**
	 * This method takes the 2D array of floats and normalizes it, ie, restricts the range of values to [0, 1]
	 * @return float[][]
	 */
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
	
	/**
	 * This method generates a 2D array of {@link Tile}s from the normalized 2D array of floats.
	 * Each possible "height" of tile has a range of values, eg, a mountain is anything above .7
	 * @return Tile[][]
	 */
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
		
		WeatherTile day = new WeatherTile(WeatherEnum.DAY);
		WeatherTile night = new WeatherTile(WeatherEnum.NIGHT);
		
		ResourceTile methane = new ResourceTile(ResourceEnum.METHANE);
		ResourceTile carbon = new ResourceTile(ResourceEnum.CARBON);
		ResourceTile iron = new ResourceTile(ResourceEnum.IRON);
		
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//we may need to switch i and j; I can never remember if the outer loop is the row or the column
				if (floatMap[i][j] > mountainThreshold) {
					if (j < size / 2) {
						tileMap[i][j] = new Tile(mountain, noResource, noBuilding, day, droneImage);
					} else {
						tileMap[i][j] = new Tile(mountain, noResource, noBuilding, night, droneImage);
					}
					if (rand.nextFloat() < .3) {
						tileMap[i][j].setResource(iron);
					}
				} else if (floatMap[i][j] > groundThreshold) {
					if (j < size / 2) {
						tileMap[i][j] = new Tile(plain, noResource, noBuilding, day, droneImage);
					} else {
						tileMap[i][j] = new Tile(plain, noResource, noBuilding, night, droneImage);
					}
					if (rand.nextFloat() < .3) {
						tileMap[i][j].setResource(carbon);
					}
				} else if (floatMap[i][j] > sandThreshold) {
					if (j < size / 2) {
						tileMap[i][j] = new Tile(sand, noResource, noBuilding, day, droneImage);
					} else {
						tileMap[i][j] = new Tile(sand, noResource, noBuilding, night, droneImage);
					}
				} else {
					if (j < size / 2) {
						tileMap[i][j] = new Tile(ocean, noResource, noBuilding, day, droneImage);
					} else {
						tileMap[i][j] = new Tile(ocean, noResource, noBuilding, night, droneImage);
					}
					if (rand.nextFloat() < .3) {
						tileMap[i][j].setResource(methane);
					}
				}
				tileMap[i][j].setX(j);
				tileMap[i][j].setY(i);
				//switched i and j here; I think that is where we were having the x/y issue
			}
		}
		return tileMap;
	}
	
	/**
	 * This method takes each {@link Tile} and sets its north, south, east, and west.
	 * @return Tile[][]
	 */
	private Tile[][] buildNodeMap() {
		Tile[][] map = buildTileMap();
		//i is column, j is row
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
	
	/**
	 * This method generates a String for the textual representation of the map.
	 * @return String
	 */
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
			return map[j][i];
	}
	
	public Tile getTile2(int i, int j) {
		int xLoop;
		int yLoop;
		if (i < 0) {
			xLoop = size + i;
		} else if (i >= size) {
			xLoop = i - size;
		} else {
			xLoop = i;
		}
		if (j < 0) {
			yLoop = size + j;
		} else if (j >= size) {
			yLoop = j - size;
		} else {
			yLoop = j;
		}
		return map[yLoop][xLoop];
	}
	
	public void setTile(Tile t, int x, int y) {
		map[y][x] = t;
	}
	
	/**
	 * This method converts {@link Tile}s to a sort that has a {@link Building}.
	 * @param b - the {@link Building} to be built.
	 */
	public void build(Building b) {
		//These allows us to find the tile in our array for the building, ie, the top left corner of it
		int x = b.getLocation().x;
		int y = b.getLocation().y;
		
		if (b.canBuild(map[x][y])) { //I think this should be switched, for the x/y issue
			allBuildings.add(b);
			//not that it currently has an issue, since we're working with squares, but
			//to be correct
			/*
			 * Just like in the loop in Building, where it checks if it can build,
			 * this loop sets each tile to have the appropriate type of building.
			 * 
			 * Note: this required a method, getTypeOfBuilding() to be added to the
			 * abstract building class. Each building needs to hold the appropriate 
			 * enum in it. This will allow us to communicate the type of the building 
			 * to the map, when we are building.
			 */
		//Fixed issue with not drawing upper left corner, not sure if the code above was for efficiency. Revision needed.
			for(int i = x; i < b.getWidth()+x;i++){
				for (int j = y; j < b.getLength()+y; j++) {
					map[i][j].setBuilding(new BuildingTile(b.getTypeOfBuilding()));
					b.addTile(map[i][j]);
					if (b.getTypeOfBuilding() == BuildingEnum.FARM) {
					}
				}	
				
			}
		}
	}
	
	//Method checks to see what item is being built, then gives the location of the 
	public Tile whereToBuild(Items item) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * This method, as per the name, finds the nearest {@link Building} of the appropriate type.
	 * @param start - the {@link Tile} for the starting location. We search for the nearest {@link Building} to this {@link Tile}.
	 * @param type - the type of {@link Building} to be found.
	 * @return Building - the {@link Building} found.
	 */
	public Building findNearest(Tile start, BuildingEnum type) {
		int buildX, buildY, startX, startY, minX, minY;
		startX = start.getX();
		startY = start.getY();
		minX = 10000;
		minY = 10000;
		Building nearestBuilding = null;
		for (Building build : allBuildings) {
			if (build.getTypeOfBuilding() == type) {
				buildX = (int) build.getLocation().getX();
				buildY = (int) build.getLocation().getY();
				if ((buildX - startX)*(buildX - startX) + (buildY - startY)*(buildY - startY) < (minX - startX)*(minX - startX) + (minY - startY)*(minY - startY)) {
					minX = buildX;
					minY = buildY;
					nearestBuilding = build;
				}
			}
		}
		return nearestBuilding;
	}
	
	/**
	 * This sets a {@link Tile} to be marked as visited for the purpose of pathfinding.
	 * @param x - the x location of the {@link Tile}
	 * @param y - the y location of the {@link Tile}
	 */
	public void pathFinderVisited(int x, int y) {
		map[x][y].setVisited(true);
		
	}

	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		
		return 1;
	}
	
	/**
	 * This clears all {@link Tile}s from being visited for the purpose of pathfinding.
	 */
	public void clearVisited() {
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map.length; j ++){
				map[i][j].setVisited(false);
			}
		}
		
	}

	public AStarPathFinder getFinder() {
		return finder;
	}	
	
	public void addToTerraformed(int n) {
		numOfTerraformedTiles += n;
	}
	
	public int getTerraformed() {
		return numOfTerraformedTiles;
	}

	public LinkedList<Building> getAllBuildings() {
		return allBuildings;
	}
}
