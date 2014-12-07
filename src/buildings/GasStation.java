package buildings;

import model.Drone;
import tiles.BuildingEnum;
import game.Map;

public class GasStation extends Building {
	
	private final static int BASE_WIDTH = 2;
	private final static int BASE_LENGTH = 2;
	private final static int MAX_CAP = 0;
	private final static String BUILDING_NAME = "N";

	public GasStation(int locX, int locY, int wid, int len, int cap, int time,
			String name, BuildingEnum type) {
		super(locX, locY, wid, len, cap, name, type);
		// TODO Auto-generated constructor stub
	}
	
	public GasStation (int x, int y) {
		super(x, y, BASE_WIDTH, BASE_LENGTH, MAX_CAP, BUILDING_NAME, BuildingEnum.GASSTATION);
	}

	@Override
	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collectResource(Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void construct() {
		// TODO Auto-generated method stub
		
	}

	public void refuel(Drone drone) {
		drone.setGas(drone.getMaxGas());
	}

}
