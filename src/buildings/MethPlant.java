package buildings;

import game.Map;

import java.util.ArrayList;

import model.Drone;
import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class MethPlant extends Building {

	private final static int METH_WIDTH = 2;
	private final static int METH_HEIGHT = 2;
	private final static String BUILDING_NAME = "MP";
	private final static int MAX_CAP = 500;

	public MethPlant(int x, int y) {
		super(x, y, METH_WIDTH, METH_HEIGHT, MAX_CAP,
				BUILDING_NAME, BuildingEnum.MATHANEPLANT);
	}

	@Override
	public void executeOnBuilding(Map map) {
		setInventory(getInventory() + 10);
		System.out.println("Daylight collected");
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

	public void cook(Drone drone) {
		if (getInventory() > 5) {
			drone.setGas(drone.getGas() + 5);
		} else {
			System.out.println("Not enough gas to give");
		}
	}
}
