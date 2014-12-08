package buildings;

import game.Map;

import java.util.ArrayList;

import model.Drone;
import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class MethanePlant extends Building {

	private final static int METH_WIDTH = 2;
	private final static int METH_HEIGHT = 2;
	private final static String BUILDING_NAME = "MP";
	private final static int MAX_CAP = 500;

	public MethanePlant(int x, int y) {
		super(x, y, METH_WIDTH, METH_HEIGHT, MAX_CAP,
				BUILDING_NAME, BuildingEnum.METHANEPLANT);
	}

	@Override
	public void executeOnBuilding(Map map) {
		setInventory(getInventory() + 10);
	}

	public void fill(Drone drone) {
		if (getMethane() > 5) {
			drone.setGas(drone.getGas() + 5);
			setMethane(getMethane()- 5);
		} else {
			System.out.println("Not enough gas to give");
		}
	}
}
