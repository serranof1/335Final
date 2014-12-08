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
			if (drone.getCurrentTile() == map.getTile((int) giveMeStuff.getLocation().getX(), (int) giveMeStuff.getLocation().getY())) {
				giveMeStuff.depositAll(drone.getInventory());
				giveMeStuff.setIron(giveMeStuff.getIron() + drone.getIron());
				giveMeStuff.setIron(giveMeStuff.getCarbon() + drone.getCarbon());
				giveMeStuff.setIron(giveMeStuff.getMethane() + drone.getMethane());
			} else {
				drone.getTaskList().push(new MoveTask(drone, map.getTile((int) giveMeStuff.getLocation().getX(), (int) giveMeStuff.getLocation().getY()), false));
				drone.getTaskList().pop().execute(map);
			}
		}
	}
}
