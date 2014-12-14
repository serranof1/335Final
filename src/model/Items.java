package model;

import resources.Resource;
import tiles.BuildingEnum;

/**
 * Items is a class for things that can be built in-game, but are not placed on the map like buildings.
 * They have a required {@link Building} in which they can be built.
 * @author Team Rosetta
 *
 */
public abstract class Items {
	protected Drone drone;
	protected BuildingEnum reqBuilding;
	
	public Items() {}
	
	public abstract void execute(Drone d);
	
	public BuildingEnum getRequiredBuilding() {
		return reqBuilding;
	}
}
