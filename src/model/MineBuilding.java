package model;

import resources.Resource;

public class MineBuilding extends Building{

	private final static int BUILD_RATE = 10;
	private final static int BUILD_COST = 50;
	private final static String BUILDING_NAME = "MB";
	
	public MineBuilding(int x, int y, int width, int length, Resource source) {
		super(x, y, width, length, source, BUILDING_NAME);
		super.setCost(BUILD_COST);
		// TODO Auto-generated constructor stub
	}

	public void startBuild() {
		super.build(BUILD_RATE);
	}
	
}
