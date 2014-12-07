package task;

import buildings.Building;
import tiles.Tile;
import model.Drone;
import game.Map;

public class RepairTask extends Task {
	Building building;
	Tile goal;
	public RepairTask(Drone drone, Building building, Tile tile) {
		super(drone);
		this.building = building;
		goal = tile;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Map map) {
		drone.setGas(drone.getGas() - 1);
		drone.setPower(drone.getPower() - 6);
		if(drone.getCurrentTile() == goal){
			if (drone.getRepair() > drone.getMaxRepair()) {
				drone.setRepair(drone.getMaxRepair());
			} else {
				drone.setRepair(drone.getRepair() + 20);
				drone.getTaskList().push(new RepairTask(drone, building, goal));
			}
		} else {
			Tile repairTile = map.getTile(10, 10);
			//drone.getTaskList().push(new ChargeTask(drone));
			drone.getTaskList().push(new MoveTask(drone, repairTile, true));
			drone.getTaskList().pop().execute(map);
		}
	}

}
