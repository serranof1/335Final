package model;

import tiles.BuildingEnum;
/**
 * Treads is an {@link Items} that improves a {@link Drone}'s movement ability, but less so than
 * a {@link Jetpack}.
 * @author Team Rosetta
 *
 */
public class Treads extends Items {
	
	public Treads() {
		super();
		reqBuilding = BuildingEnum.ENGINEERING;
	}
	@Override
	/**
	 * This method improves a {@link Drone}'s movement ability.
	 * @param d - The {@link Drone} to improve.
	 */
	public void execute(Drone d) {
		d.setMovementAbility(2);
	}

}
