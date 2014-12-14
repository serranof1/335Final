package model;

import tiles.BuildingEnum;
/**
 * Battery is an {@link Items} used to increase the max power of a Drone.
 * @author Team Rosetta
 *
 */
public class Battery extends Items {
	
	public Battery() {
		super();
		reqBuilding = BuildingEnum.POWERPLANT;
	}

	/**
	 * @param drone - The {@link Drone} to increase max power.
	 */
	@Override
	public void execute(Drone drone) {
		drone.setMaxPower(1000);
		drone.setPower(1000);
		System.out.println("LOTS AND LOTS OF POWER NOW");
	}
}
