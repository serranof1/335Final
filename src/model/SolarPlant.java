package model;

import resources.Resource;

public class SolarPlant extends Building {

	private final static int BUILD_RATE = 10;
	private final static int BUILD_COST = 50;
	
	public SolarPlant(int x, int y, int width, int length, Resource source) {
		super(x, y, width, length, source);
		super.setCost(BUILD_COST);
		// TODO Auto-generated constructor stub
	}

	public void getBuild() {
		super.build(BUILD_RATE);
	}
}
