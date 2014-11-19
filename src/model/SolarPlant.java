package model;

import resources.Resource;

public class SolarPlant extends Building {

	private final static int BUILD_RATE = 10;
	private final static int BUILD_COST = 50;
	private final static String BUILDING_NAME = "SP";
	
	public SolarPlant(int x, int y, int width, int length, Resource source) {
		super(x, y, width, length, source, BUILDING_NAME);
		super.setCost(BUILD_COST);
		// TODO Auto-generated constructor stub
	}

	public void getBuild() {
		super.build(BUILD_RATE);
	}
}
