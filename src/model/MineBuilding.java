package model;

import resources.Resource;

public class MineBuilding extends Building{

	private final static int BUILD_RATE = 10;
	
	public MineBuilding(int x, int y, int width, int length, Resource source) {
		super(x, y, width, length, source);
		// TODO Auto-generated constructor stub
	}

	public void startBuild() {
		super.getBuild(BUILD_RATE);
	}
	
}
