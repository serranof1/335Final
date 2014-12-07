package task;

import tiles.Tile;
import model.Drone;
import game.Map;

public class WaitTask extends Task {

	Tile goal;
	
	public WaitTask(Drone drone, Tile goal) {
		super(drone);
		this.goal = goal;
	}

	@Override
	public void execute(Map map) {
		drone.setGas(drone.getGas() - 1);
		drone.setPower(drone.getPower() - 1);
		drone.setRepair(drone.getRepair() - 1);
		drone.getTaskList().push(new MoveTask(drone, goal, true));

	}

}
