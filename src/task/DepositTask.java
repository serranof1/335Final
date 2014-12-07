package task;

import buildings.Building;
import model.Drone;
import game.Map;

public class DepositTask extends Task {
	
	Building giveMeStuff;

	public DepositTask(Drone drone, Building giveMeStuff) {
		super(drone);
		// TODO Auto-generated constructor stub
		this.giveMeStuff = giveMeStuff;
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 3);
		drone.setGas(drone.getGas() - 1);
		drone.setRepair(drone.getRepair() - 1);
		if (drone.isFull()) {
			if (drone.getCurrentTile() == map.getTile(10, 10)) {
				giveMeStuff.depositAll(drone.getInventory());
			} else {
				drone.getTaskList().push(new MoveTask(drone, map.getTile(10, 10), true));
				drone.getTaskList().pop().execute(map);
			}
		}
	}
}
