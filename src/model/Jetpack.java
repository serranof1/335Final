package model;

import tiles.BuildingEnum;

/**
 * Jetpack is an {@link Items} that increases the movement ability of a {@link Drone}, thereby allowing
 * it to travel over mountains, etc.
 * @author Gateway
 *
 */
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
