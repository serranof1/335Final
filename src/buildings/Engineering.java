package buildings;

import game.Map;

import java.util.ArrayList;

import model.Drone;
import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class Engineering extends Building {

	private final static int ENG_WIDTH = 3;
	private final static int ENG_HEIGHT = 3;
	private final static String BUILDING_NAME = "E";
	private final static int MAX_CAP = 100;
	private final static int BUILD_TIME = 4;
	
	private boolean createDrone = false;
	
	
	public Engineering(int x, int y) {
		super(x, y, ENG_WIDTH, ENG_HEIGHT, MAX_CAP,
				BUILDING_NAME, BuildingEnum.ENGINEERING);
	}

	@Override
	public void executeOnBuilding(Map map) {
		
	}
	
	public void repair(Drone drone) {
		// TODO Auto-generated method stub
		if (getInventory() > 2) {
			drone.setRepair(drone.getRepair() + 2);
			setIron(getIron()-2);
			System.out.println("Engineering: " + getIron());
		} else {
			System.out.println("Not enough to repair");
		}
	}
}
