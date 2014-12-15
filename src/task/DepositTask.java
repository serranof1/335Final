package task;

import buildings.Building;
import model.Drone;
import game.Map;

/**
 * DepositTask is a {@link Task} in which a {@link Drone} deposits resources into a {@link Building}.
 * @author Team Rosetta
 *
 */
public class DepositTask extends Task {
	
	Building giveMeStuff;

	public DepositTask(Drone drone, Building giveMeStuff) {
		super(drone);
		this.giveMeStuff = giveMeStuff;
	}
	
	/**
	 * This method adds all of the {@link Drone}'s resources to the designated {@link Building}. If it
	 * is not in the correct location, the {@link Drone} is assigned a new {@link MoveTask}.
	 * @param map - The {@link Map} on which to execute.
	 */
	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 3);
		drone.setGas(drone.getGas() - 1);
		drone.setRepair(drone.getRepair() - 1);

		if (drone.getCurrentTile() == map.getTile((int) giveMeStuff.getLocation().getX(), (int) giveMeStuff.getLocation().getY())) {
			drone.depositResources(giveMeStuff);
			System.out.println("Attempting to deposit");
			drone.getTaskList().pop();
			drone.toggleCollecting();
		} else {
			drone.getTaskList().push(new MoveTask(drone, map.getTile((int) giveMeStuff.getLocation().getX(), (int) giveMeStuff.getLocation().getY()), false));
			drone.getTaskList().peek().execute(map);
		}
	}
}
