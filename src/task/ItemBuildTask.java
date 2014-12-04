package task;

import model.Drone;
import model.Items;
import tiles.Tile;
import game.Map;

public class ItemBuildTask extends Task {
	
	Items toBeBuilt;
	
	public ItemBuildTask(Drone drone, Items item) {
		super(drone);
		toBeBuilt = item;
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 7);
		drone.setRepair(drone.getRepair() - 1);
		if (drone.getCurrentTile().getBuilding().getBuildingType() == toBeBuilt.getRequiredBuilding()) {
			toBeBuilt.execute(drone);
			drone.giveItem(toBeBuilt);
			System.out.println("I made myself a battery!");
		} else {
			Tile chargingTile = map.getTile(10, 15);
			drone.getTaskList().push(new ItemBuildTask(drone, toBeBuilt));
			drone.getTaskList().push(new MoveTask(drone, chargingTile, true));
			drone.getTaskList().pop().execute(map);
			System.out.println("Moving To Power Plant to make a battery!");
		}
	}

}
