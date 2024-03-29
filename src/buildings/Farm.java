package buildings;

import tiles.BuildingEnum;
import tiles.GroundEnum;
import tiles.GroundTile;
import tiles.Tile;
import game.Map;
import java.util.Random;
/**
 * Farm is a {@link Building} used to terraform the planet. It converts inhospitable {@link Tile}s to
 * hospitable ones, as part of the win condition.
 * @author Team Rosetta
 *
 */
public class Farm extends Building {
	private final static Random rand = new Random();
	private final static int BASE_WIDTH = 2;
	private final static int BASE_LENGTH = 2;
	private final static int MAX_CAP = 0;
	private final static String BUILDING_NAME = "F";
	int xGrass, yGrass, widGrass, lenGrass,  max;
	//carbon will need to eventually be three separate values for resources, or something.
	private final GroundTile grass = new GroundTile(GroundEnum.GRASS);

	public Farm(int locX, int locY, int wid, int len, int cap,
			String name, BuildingEnum type) {
		super(locX, locY, wid, len, cap, name, type);
		// TODO Auto-generated constructor stub
		xGrass = locX - 1;
		yGrass = locY - 1;
		widGrass = wid + 1;
		lenGrass = len + 1;
		carbon = 5;
		max = 0;
	}
	
	public Farm(int x, int y) {
		super(x, y, BASE_WIDTH, BASE_LENGTH, MAX_CAP, BUILDING_NAME, BuildingEnum.FARM);
		xGrass = x - 1;
		yGrass = y - 1;
		widGrass = BASE_WIDTH + 1;
		lenGrass = BASE_LENGTH + 1;
		carbon = 5;
		max = 0;
	}
	
	/**
	 * This method converts inhospitable {@link Tile}s  to hospitable ones, provided resources are available.
	 * @param map - The {@link Map} we are changing tiles on.
	 */
	@Override
	public void executeOnBuilding(Map map) {
		map.getTile(0, 0).setGround(grass);
		carbon = map.getAllBuildings().get(0).getCarbon();
		if (carbon > 0 && max < 10) {
			//This will need to be converted to node-logic.
		for (int i = yGrass; i < yGrass + lenGrass + 1; i++) {
			if (rand.nextFloat() > .2){
				//map.getTile2(xGrass, i).setGround(grass);
				map.getTile2(i, xGrass).setGround(grass);
				//map.getTile2(xGrass + widGrass, i).setGround(grass);
				map.getTile2(i, xGrass + widGrass).setGround(grass);
				map.addToTerraformed(1);
			}
		}
		for (int i = xGrass; i < xGrass + widGrass + 1; i++) {
			if (rand.nextFloat() > .2) {
				//map.getTile2(i, yGrass).setGround(grass);
				map.getTile2(yGrass, i).setGround(grass);
				//map.getTile2(i, yGrass + lenGrass).setGround(grass);
				map.getTile2(yGrass + lenGrass, i).setGround(grass);
				map.addToTerraformed(1);
			}
		}
		xGrass--;
		yGrass--;
		widGrass += 2;
		lenGrass += 2;
		map.addToTerraformed(-2);
		map.getAllBuildings().get(0).setCarbon(-100);
		max++;
		}
	}
	
	@Override
	public boolean canBuild(Tile t) {
		return true;
	}
}
