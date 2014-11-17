package game;


import java.util.Random;



public class DiamondSquare {
	private float mountainThreshold, groundThreshold, oceanThreshold, sandThreshold;
	private int n, size;
	private Random rand;
	
	public DiamondSquare() {
		//oceanThreshold = .5f;
		mountainThreshold = .85f;
		groundThreshold = .45f;
		sandThreshold = .3f;
		
		n = 5;
		rand = new Random();
		long seed = rand.nextLong();
		System.out.println("You random seed is " + seed);
		rand = new Random(seed);
		
		size = (int) Math.pow(2, n) + 1;
	}
	/*
	 * With:
	 * //experimentation with initializing more points. Uncomment the above to default to the corners
		for (int i = 0; i < size; i += size / 4) {
			for (int j = 0; j < size; j += size / 4) {
				map[i][j] = rand.nextFloat();
			}
		}
		and
		int step = (int) Math.pow(2, n) / 8; 
		878650448576139298 is a nice seed
	 */
	
	public DiamondSquare(long seed) {
		//oceanThreshold = .5f;
		mountainThreshold = .85f;
		groundThreshold = .45f;
		sandThreshold = .3f;
		
		n = 5;
		rand = new Random(seed);
		
		size = (int) Math.pow(2, n) + 1;
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
	
	private void demoMap() {
		float[][] map = normalizeMap();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (map[x][y] > mountainThreshold) {
					System.out.print("[M]");
				} else if (map[x][y] > groundThreshold) {
					System.out.print("[_]");
				} else if (map[x][y] > sandThreshold) {
					System.out.print("[,]");
				} else {
					System.out.print("[~]");
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		DiamondSquare test = new DiamondSquare();
		//test.demoMap();
	}
}

