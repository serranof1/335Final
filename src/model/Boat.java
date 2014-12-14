package model;

import tiles.BuildingEnum;

public class Boat extends Items {
	
	public Boat() {
		super();
		reqBuilding = BuildingEnum.ENGINEERING;
	}
	@Override
	public void execute(Drone d) {
		d.setMovementAbility(5);
	}

}
