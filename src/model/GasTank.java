package model;

import tiles.BuildingEnum;

public class GasTank extends Items{

	public GasTank() {
		super();
		reqBuilding = BuildingEnum.METHANEPLANT;
	}
	
	@Override
	public void execute(Drone d) {
		drone.setMaxGas(1000);
		drone.setGas(1000);
		System.out.println("LOTS AND LOTS OF GAS NOW");
	}

}
