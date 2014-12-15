package model;

import tiles.BuildingEnum;
/**
 * GasTank is an {@link Items} used to increase the maximum gas of a {@link Drone}.
 * @author Team Rosetta
 *
 */
public class GasTank extends Items{

	public GasTank() {
		super();
		reqBuilding = BuildingEnum.METHANEPLANT;
	}
	
	/**
	 * This method increases the maximum gas a {@link Drone} can have.
	 * param d - The {@link Drone} to increase max gas.
	 */
	@Override
	public void execute(Drone d) {
		drone.setMaxGas(1000);
		drone.setGas(1000);
		System.out.println("LOTS AND LOTS OF GAS NOW");
	}

}
