package model;

import resources.Resource;

public class MineBuilding extends Building{

	private final static int BUILD_RATE = 10;
	private final static int BUILD_COST = 50;
	private final static int MINE_WIDTH = 2;
	private final static int MINE_HEIGHT = 2;
	private final static String BUILDING_NAME = "MB";
	
	public MineBuilding(int x, int y, Resource source) {
		super(x, y, MINE_WIDTH, MINE_HEIGHT, source, BUILDING_NAME);
		super.setCost(BUILD_COST);
	}

	public void startBuild() {
		super.build(BUILD_RATE);
	}
	
	private class MineItem extends Items {

		public MineItem() {
			super(getBuildingResource());
		}
	}
}
