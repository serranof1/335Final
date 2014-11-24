package model;

import tiles.BuildingEnum;

public class Battery extends Items {
	
	public Battery() {
		super();
		reqBuilding = BuildingEnum.POWERPLANT;
	}

	@Override
	public void execute(Drone drone) {
		drone.setMaxPower(1000);
		drone.setPower(1000);
		System.out.println("LOTS AND LOTS OF POWER NOW");
	}
}
