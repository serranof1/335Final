package model;

import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class Base extends Building {

	private final static String BUILDING_NAME = "B";
	private final static int BASE_WIDTH = 4;
	private final static int BASE_HEIGHT = 4;
	
	public Base(int x, int y, Tile base) {
		super(x, y, BASE_WIDTH, BASE_HEIGHT,  null, BUILDING_NAME);
		//super.setBuildSite(base);
		typeOfBuilding = BuildingEnum.BASE;
	}
}
