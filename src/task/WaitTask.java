package task;

import tiles.Tile;
import model.Drone;
import game.Map;

/**
 * WaitTask is a {@link Task} in which a {@link Task} in which a Drone sits in one place.
 * @author Team Rosetta
 *
 */
public class WaitTask extends Task {

	Tile goal;
	
	public WaitTask(Drone drone, Tile goal) {
		super(drone);
		this.goal = goal;
	}

	/**
	 * This method decrements the {@link Drone}'s needs.
	 * @param map - The {@link Map} on which to execute.
	 */
	@Override
	public void execute(Map map) {
		drone.setGas(drone.getGas() - 1);
		drone.setPower(drone.getPower() - 1);
		drone.setRepair(drone.getRepair() - 1);
		drone.getTaskList().push(new MoveTask(drone, goal, true));

	}

}
