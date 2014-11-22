package model;

import tiles.BuildingEnum;

public class Battery extends Items {
	
	public Battery() {
		super();
		reqBuilding = BuildingEnum.POWERPLANT;
	}

	@Override
	public void execute(Drone drone) {
		drone.setPower(10000);
		System.out.println("It's over 9000");
	}
}
