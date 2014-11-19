package model;

import resources.Resource;

public class SolarPlant extends Building {

	private final static int BUILD_RATE = 10;
	public SolarPlant(int x, int y, int width, int length, Resource source) {
		super(x, y, width, length, source);
		// TODO Auto-generated constructor stub
	}

	public void getBuild() {
		super.getBuild(BUILD_RATE);
	}
}
