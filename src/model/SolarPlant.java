package model;

import resources.Resource;
import tiles.Tile;

public class SolarPlant extends Building {

	private final static int BUILD_RATE = 10;
	private final static int BUILD_COST = 50;
	private final static String BUILDING_NAME = "SP";
	
	public SolarPlant(int x, int y, int width, int length, Resource source, Tile tile) {
		super(x, y, width, length, source, BUILDING_NAME);
		super.setBuildSite(tile);
		super.setCost(BUILD_COST);
	}

	public void getBuild() {
		super.build(BUILD_RATE);
	}
	
	private class Battery extends Items {

		public Battery() {
			super(getBuildingResource());
		}
		
	}
}
