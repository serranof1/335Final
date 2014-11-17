package game;

import java.util.Random;

import resources.*;
import tiles.*;

public class Map2 {
	private Tile2[][] map;
	private int size, n;
	private long seed;
	private Random rand;
	
	private float mountainThreshold = .9f;
	private float groundThreshold = .35f;
	//I think, for a map of any particular size, the linkedlist of resources becomes difficult to use
	//so I did not implement it here. We may need to figure this out.
	
	public Map2(int n, long seed) {
		this.n = n;
		size = (int) Math.pow(2, n) + 1;
		this.seed = seed;
		rand = new Random(seed);
		map = buildNodeMap();
	}
	
	public Map2(int n) {
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

	private Tile2[][] buildTileMap() {
		Tile2[][] tileMap = new Tile2[size][size];
		float[][] floatMap = normalizeMap();
		
		Mountain mountain = new Mountain();
		Ground ground = new Ground();
		Ocean ocean = new Ocean();
		
		NoResource nr = new NoResource(null, 0);
		MethaneTile methaneTile = new MethaneTile(new Methane(), 0);
		MineralTile mineralTile = new MineralTile(new Mineral(), 0);
		
		NoWeather nw = new NoWeather();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (floatMap[i][j] > mountainThreshold) {
					if (rand.nextFloat() > .75) {
						tileMap[i][j] = new Tile2(mountain, mineralTile, nw);
					} else {
						tileMap[i][j] = new Tile2(mountain, nr, nw);
					}
				} else if (floatMap[i][j] > groundThreshold) {
					if (rand.nextFloat() > .9) {
						tileMap[i][j] = new Tile2(ground, mineralTile, nw);
					} else {
						tileMap[i][j] = new Tile2(ground, nr, nw);
					}
				} else {
					tileMap[i][j] = new Tile2(ocean, methaneTile, nw);
				}
			}
		}
		return tileMap;
	}
	
	
	private Tile2[][] buildNodeMap() {
		Tile2[][] map = buildTileMap();
		for(int i = 0; i < size; i++){
			for(int j =0; j < size; j++){
				map[i][j].setWest(map[(size - i) % size][j]);
				map[i][j].setEast(map[(size + i) % size][j]);
				map[i][j].setNorth(map[i][(size - 1) % size]);
				map[i][j].setSouth(map[i][(size + 1) % size]);
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
	
	public Tile2 getTile(int i, int j) {
		return map[i][j];
	}
	
	public static void main(String[] args) {
		Map2 test = new Map2(5);
		System.out.println(test.drawTextForm());
	}
}
