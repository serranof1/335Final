package task;

import game.Map;
import model.Drone;
import tiles.Tile;
import buildings.Building;
import buildings.Engineering;
import buildings.SolarPlant;

/**
 * RepairTask is a {@link Task} that assigns a {@link Drone} to find a {@link Engineering} and repair.
 * @author Team Rosetta
 *
 */
public class RepairTask extends Task {
	Engineering building;
	Tile goal;
	public RepairTask(Drone drone, Building building, Tile tile) {
		super(drone);
		this.building = (Engineering) building;
		goal = tile;
		drone.toggleRepair();
	}
	/**
	 * @param map - The {@link Map} on which to execute. The {@link Drone}'s needs are decremented and
	 * the drone is repaired. If it is not at a {@link Engineering} it is assigned a {@link MoveTask}.
	 */
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
				drone.toggleRepair();
			}
		} else {
			drone.getTaskList().push(new MoveTask(drone, building.getEmptyTile(), false));
			drone.getTaskList().peek().execute(map);
		}
	}

}
