package model;

import tiles.BuildingEnum;

public class Treads extends Items {
	
	public Treads() {
		super();
		reqBuilding = BuildingEnum.ENGINEERING;
	}
	@Override
	public void execute(Drone d) {
		d.setMovementAbility(2);
	}

}
