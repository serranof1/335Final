package model;

import game.Map;

public class ItemBuildTask extends Task {
	
	Items toBeBuilt;
	
	public ItemBuildTask(Drone drone, Items item) {
		super(drone);
		toBeBuilt = item;
	}

	@Override
	public void execute(Map map) {
		if (true) {
			toBeBuilt.execute(drone);
			drone.giveItem(toBeBuilt);
		}
	}

}
