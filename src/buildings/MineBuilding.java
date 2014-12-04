package buildings;

import game.Map;
import resources.Resource;
import tiles.BuildingEnum;

public class MineBuilding extends Building{

	private final static int BUILD_COST = 50;
	private final static int MINE_WIDTH = 2;
	private final static int MINE_LENGTH = 2;
	private final static String BUILDING_NAME = "MB";
	private final static int MAX_CAP = 10000;
	private final static int BUILD_TIME = 8;
	
	public MineBuilding(int x, int y, Resource source) {
		super(x, y, MINE_WIDTH, MINE_LENGTH, MAX_CAP, BUILD_TIME, BUILDING_NAME, BuildingEnum.MINE);
	}

	@Override
	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void construct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collectResource(Map map) {
		// TODO Auto-generated method stub
		
	}
}
