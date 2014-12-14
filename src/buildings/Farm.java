package buildings;

import tiles.BuildingEnum;
import tiles.GroundEnum;
import tiles.GroundTile;
import tiles.Tile;
import game.Map;
/**
 * Farm is a {@link Building} used to terraform the planet. It converts inhospitable {@link Tile}s to
 * hospitable ones, as part of the win condition.
 * @author Team Rosetta
 *
 */
public class Farm extends Building {
	private final static int BASE_WIDTH = 2;
	private final static int BASE_LENGTH = 2;
	private final static int MAX_CAP = 0;
	private final static String BUILDING_NAME = "F";
	int xGrass, yGrass, widGrass, lenGrass, amount;
	//amount will need to eventually be three separate values for resources, or something.
	private final GroundTile grass = new GroundTile(GroundEnum.GRASS);

	public Farm(int locX, int locY, int wid, int len, int cap,
			String name, BuildingEnum type) {
		super(locX, locY, wid, len, cap, name, type);
		// TODO Auto-generated constructor stub
		xGrass = locX - 1;
		yGrass = locY - 1;
		widGrass = wid + 1;
		lenGrass = len + 1;
		amount = 1;
	}
	
	public Farm(int x, int y) {
		super(x, y, BASE_WIDTH, BASE_LENGTH, MAX_CAP, BUILDING_NAME, BuildingEnum.FARM);
		xGrass = x - 1;
		yGrass = y - 1;
		widGrass = BASE_WIDTH + 1;
		lenGrass = BASE_LENGTH + 1;
		amount = 3;
	}
	
	/**
	 * This method converts inhospitable {@link Tile}s  to hospitable ones, provided resources are available.
	 * @param map - The {@link Map} we are changing tiles on.
	 */
	@Override
	public void executeOnBuilding(Map map) {
		map.getTile(0, 0).setGround(grass);
		if (amount > 0) {
			//This will need to be converted to node-logic.
		for (int i = yGrass; i < yGrass + lenGrass + 1; i++) {
			map.getTile(xGrass, i).setGround(grass);
			map.getTile(xGrass + widGrass, i).setGround(grass);
			map.addToTerraformed(2);
		}
		for (int i = xGrass; i < xGrass + widGrass + 1; i++) {
			map.getTile(i, yGrass).setGround(grass);
			map.getTile(i, yGrass + lenGrass).setGround(grass);
			map.addToTerraformed(2);
		}
		xGrass--;
		yGrass--;
		widGrass += 2;
		lenGrass += 2;
		map.addToTerraformed(-2);
		amount--;
		}
	}
	
	@Override
	public boolean canBuild(Tile t) {
		return true;
	}
	
	public void deposit(int n) {
		amount = n;
	}

}
