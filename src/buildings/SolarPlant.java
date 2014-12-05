package buildings;

import game.Map;

import java.util.ArrayList;

import model.Drone;
import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class SolarPlant extends Building {

	private final static int SOLAR_WIDTH = 2;
	private final static int SOLAR_HEIGHT = 2;
	private final static String BUILDING_NAME = "SP";
	private final static int MAX_CAP = 5000;
	private final static int BUILD_TIME = 4;

	public SolarPlant(int x, int y) {
		super(x, y, SOLAR_WIDTH, SOLAR_HEIGHT, MAX_CAP, BUILD_TIME,
				BUILDING_NAME, BuildingEnum.POWERPLANT);
	}

	@Override
	public void executeOnBuilding(Map map) {
		if (isFinished()) {
			for (Tile tile : getTileList()) {
				if (tile.daytime()) {
					setInventory(getInventory() + 25);
					System.out.println("Daylight collected");
				}
			}
		}
	}

	@Override
	public void construct() {
		// TODO Auto-generated method stub

	}

	@Override
	public void collectResource(Map map) {
		// TODO Auto-generated method stub

	}

	public void charge(Drone drone) {
		// TODO Auto-generated method stub
		if (getInventory() > 25) {
			drone.setPower(drone.getPower() + 25);
		} else {
			System.out.println("Not enough power to give");
		}
	}
}
