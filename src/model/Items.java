package model;

import resources.Resource;
import tiles.BuildingEnum;

public abstract class Items {
	protected Drone drone;
	protected BuildingEnum reqBuilding;
	
	public Items() {}
	
	public abstract void execute(Drone d);
}
