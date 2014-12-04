package task;

import tiles.Tile;
import model.Drone;
import game.Map;

public class RepairTask extends Task {

	public RepairTask(Drone drone) {
		super(drone);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Map map) {
		// TODO Auto-generated method stub
		if(drone.getCurrentTile() == map.getTile(10, 10)){
			if (drone.getRepair() > drone.getMaxRepair()) {
				drone.setRepair(drone.getMaxRepair());
			} else {
				drone.setRepair(drone.getRepair() + 20);
				drone.getTaskList().push(new RepairTask(drone));
			}
		} else {
			Tile repairTile = map.getTile(10, 10);
			//drone.getTaskList().push(new ChargeTask(drone));
			drone.getTaskList().push(new MoveTask(drone, repairTile));
			drone.getTaskList().pop().execute(map);
		}
	}

}
