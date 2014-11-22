package model;

import resources.Resource;
import tiles.BuildingEnum;
import tiles.Tile;

public class SolarPlant extends Building {

	private final static int BUILD_RATE = 10;
	private final static int BUILD_COST = 50;
	private final static int SOLAR_WIDTH = 2;
	private final static int SOLAR_HEIGHT = 2;
	private final static String BUILDING_NAME = "SP";
	private final static Resource res = new Resource() {
	};
	
	public SolarPlant(int x, int y, Resource source, Tile tile) {
		super(x, y, SOLAR_WIDTH, SOLAR_HEIGHT, res, BUILDING_NAME);
		//super.setBuildSite(tile);
		super.setCost(BUILD_COST);
		typeOfBuilding = BuildingEnum.POWERPLANT;
	}

	public void getBuild() {
		super.build(BUILD_RATE);
	}
}
