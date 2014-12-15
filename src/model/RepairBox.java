package model;

import tiles.BuildingEnum;

/**
 * Repairbox is an {@link Items} that increases the max possible repair for a {@link Drone}
 * @author Team Rosetta
 *
 */
public class RepairBox extends Items {

	public RepairBox() {
		super();
		reqBuilding = BuildingEnum.ENGINEERING;
	}
	/**
	 * This method increases the max possible repair for the {@link Drone}.
	 * @param d - The {@link Drone} to increase max repair.
	 */
	@Override
	public void execute(Drone d) {
		// TODO Auto-generated method stub
		drone.setMaxRepair(1000);
		drone.setRepair(1000);
		System.out.println("SUPER STURDY");
	}

}
