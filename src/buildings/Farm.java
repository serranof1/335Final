package buildings;

import tiles.BuildingEnum;
import tiles.GroundEnum;
import tiles.GroundTile;
import game.Map;

public class Farm extends Building {
	int xGrass, yGrass, widGrass, lenGrass, amount;
	private final GroundTile grass = new GroundTile(GroundEnum.GRASS);

	public Farm(int locX, int locY, int wid, int len, int cap, int time,
			String name, BuildingEnum type) {
		super(locX, locY, wid, len, cap, time, name, type);
		// TODO Auto-generated constructor stub
		xGrass = locX;
		yGrass = locY;
		widGrass = wid;
		lenGrass = len;
		amount = 0;
	}

	@Override
	public void executeOnBuilding(Map map) {
		if (amount > 0) {
			//This will need to be converted to node-logic.
		for (int i = yGrass; i < yGrass + lenGrass; i++) {
			map.getTile(xGrass, i).setGround(grass);
			map.getTile(xGrass + widGrass, i).setGround(grass);
			map.addToTerraformed(2);
		}
		for (int i = xGrass; i < xGrass + widGrass; i++) {
			map.getTile(i, yGrass).setGround(grass);
			map.getTile(i, yGrass + lenGrass).setGround(grass);
			map.addToTerraformed(2);
		}
		map.addToTerraformed(-2);
		amount--;
		}
	}

	@Override
	public void collectResource(Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void construct() {
		
	}
	
	public void deposit(int n) {
		amount = n;
	}

}
