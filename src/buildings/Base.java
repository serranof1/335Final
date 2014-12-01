package buildings;

import java.util.ArrayList;

import model.Drone;
import game.Map;
import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class Base extends Building {

	private final static String BUILDING_NAME = "B";
	private final static int BASE_WIDTH = 4;
	private final static int BASE_HEIGHT = 4;
	
	public Base(int x, int y, Tile base) {
		super(x, y, BASE_WIDTH, BASE_HEIGHT, BUILDING_NAME);
		//super.setBuildSite(base);
		typeOfBuilding = BuildingEnum.BASE;
		droneList = new ArrayList<Drone>();
		tileList = new ArrayList<Tile>(16);
	}

	@Override
	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		
	}
}
