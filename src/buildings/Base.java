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
	private final static int BASE_LENGTH = 4;
	private final static int MAX_CAP = 35000;
	private int methane = 15000;
	private int electricity = 15000;
	private int iron = 2500;
	private int carbon =2500;
	
	
	public Base(int x, int y) {
		super(x, y, BASE_WIDTH, BASE_LENGTH, MAX_CAP, BUILDING_NAME, BuildingEnum.BASE);
		setInventory(35000);
	}

	@Override
	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		
	}



	public void collectResource(Map map) {
		// TODO Auto-generated method stub
		
	}
}
