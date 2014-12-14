package buildings;

import model.Drone;
import game.Map;
import resources.Resource;
import tiles.BuildingEnum;
import tiles.GroundEnum;
import tiles.ResourceEnum;
import tiles.Tile;

public class MineBuilding extends Building{

	private final static int BUILD_COST = 50;
	private final static int MINE_WIDTH = 2;
	private final static int MINE_LENGTH = 2;
	private final static String BUILDING_NAME = "MB";
	private final static int MAX_CAP = 10000;
	private final static int BUILD_TIME = 8;
	
	public MineBuilding(int x, int y, Resource source) {
		super(x, y, MINE_WIDTH, MINE_LENGTH, MAX_CAP, BUILDING_NAME, BuildingEnum.MINE);
	}

	@Override
	public void executeOnBuilding(Map map) {
		for (Tile tile : tileList) {
			if (tile.getResource().getResource() == ResourceEnum.CARBON) {
				carbon += 5;
			}
			if (tile.getResource().getResource() == ResourceEnum.IRON) {
				iron += 5;
			}
		}
	}
	
	@Override
	public boolean canBuild(Tile startTile) {

		Tile curr = null;
		Tile rowStart = null;

		curr = startTile;
		for (int i = 0; i < width; i++) {
			if (curr.getGround() == null) {
				return false;
			}
			if (curr.getSouth() != null) {
				rowStart = curr.getSouth();
			} // The start of the next row is one south of the starting node.
			/*
			 * A diagram of the node behavior: 1 2 3 4 5 6 7 8 9
			 * 
			 * Starting at curr = 1, rowStart is set to 4 Then, curr moves east
			 * 1 -> 2 -> 3 (ie, until we hit the end of the internal loop of
			 * length <required width> Next, curr is set to rowStart, ie, 4 and
			 * rowStart is set to south of curr, ie, 7. Then, curr moves east 4
			 * -> 5 -> 6, repeating the process. This continues until the other
			 * loop of length <required height> finishes
			 */

			for (int j = 0; j < length; j++) {
				if (curr.getGround().getGround() == GroundEnum.PLAIN
						|| curr.getGround().getGround() == GroundEnum.SAND
						|| curr.getGround().getGround() == GroundEnum.MOUNTAIN) { 
					curr = curr.getEast();
				} else {
					return false;
				}
			}
			curr = rowStart;
		}
		return true;

	}
	
	public void giveResource(Drone drone) {
		if (iron > 0) {
			drone.gather(drone.getIron() + 5, ResourceEnum.IRON);
			iron -= 5;
		}
		if (carbon > 0) {
			drone.gather(drone.getCarbon() + 5, ResourceEnum.CARBON);
			carbon -= 5;
		}
	}
}
