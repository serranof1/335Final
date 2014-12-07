package model;

import tiles.BuildingEnum;

public class Jetpack extends Items {
	public Jetpack() {
		super();
		reqBuilding = BuildingEnum.ENGINEERING;
	}
	@Override
	public void execute(Drone d) {
		d.setMovementAbility(10);
	}
}
