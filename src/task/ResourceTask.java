package task;

import buildings.Building;
import model.Drone;
import game.Map;

public class ResourceTask extends Task {
	
	Building giveMeStuff;

	public ResourceTask(Drone drone, Building giveMeStuff) {
		super(drone);
		// TODO Auto-generated constructor stub
		this.giveMeStuff = giveMeStuff;
	}

	@Override
	public void execute(Map map) {
		//drone.getTaskList().pop();
//		drone.setPower(drone.getPower() - 6);
		if (drone.isFull()) {
			if (drone.getCurrentTile() == map.getTile(10, 10)) {
				giveMeStuff.depositAll(drone.getInventory());
			} else {
				drone.getTaskList().push(new MoveTask(drone, map.getTile(10, 10)));
				drone.getTaskList().pop().execute(map);
			}
		}
	}
}
