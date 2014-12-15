package task;

import model.Drone;
import model.Items;
import tiles.Tile;
import game.Map;

/**
 * ItemBuiltTask is a {@link Task} in which an {@link Items} is built and given to a {@link Drone}.
 * @author Team Rosetta
 *
 */
public class ItemBuildTask extends Task {
	
	Items toBeBuilt;
	Tile goal;
	
	public ItemBuildTask(Drone drone, Items item, Tile buildLoc) {
		super(drone);
		toBeBuilt = item;
		goal = buildLoc;
	}
	
	/**
	 * This method, as all the others, decrements the needs of the {@link Drone} and adds the {@link Items}
	 * to the {@link Drone}. If not in the correct location, it is assigned a new {@link MoveTask}.
	 * @param map - The {@link Map} on which to execute.
	 */
	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 7);
		drone.setGas(drone.getGas() - 3);
		drone.setRepair(drone.getRepair() - 1);
			if (drone.getCurrentTile() == goal) {
				toBeBuilt.execute(drone);
				drone.giveItem(toBeBuilt);
				drone.getTaskList().pop();
				System.out.println("I made myself a battery!");
			} else {
				drone.getTaskList().push(new MoveTask(drone, goal, true));
				drone.getTaskList().peek().execute(map);
				System.out.println("Moving To Power Plant to make a battery!");
			}
		}
}
