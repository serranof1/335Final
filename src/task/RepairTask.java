package task;

import game.Map;
import model.Drone;
import tiles.Tile;
import buildings.Building;
import buildings.Engineering;

public class RepairTask extends Task {
	Engineering building;
	Tile goal;
	public RepairTask(Drone drone, Building building, Tile tile) {
		super(drone);
		this.building = (Engineering) building;
		goal = tile;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Map map) {
		drone.setGas(drone.getGas() - 1);
		drone.setPower(drone.getPower() - 6);
		if(drone.getCurrentTile() == goal){
			if (drone.getRepair() < drone.getMaxRepair()) {
				building.repair(drone);				
				System.out.println("REPAIRING");
			} else {
				drone.setRepair(drone.getMaxRepair());
				drone.getTaskList().pop();
			}
		} else {
			drone.getTaskList().push(new MoveTask(drone, building.getEmptyTile(), false));
			drone.getTaskList().peek().execute(map);
		}
	}

}
