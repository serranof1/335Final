package model;

import tiles.BuildingEnum;

public class RepairBox extends Items {

	public RepairBox() {
		super();
		reqBuilding = BuildingEnum.ENGINEERING;
	}
	@Override
	public void execute(Drone d) {
		// TODO Auto-generated method stub
		drone.setMaxRepair(1000);
		drone.setRepair(1000);
		System.out.println("SUPER STURDY");
	}

}
