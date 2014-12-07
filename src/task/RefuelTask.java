package task;

import buildings.Building;
import buildings.GasStation;
import tiles.Tile;
import model.Drone;
import game.Map;

public class RefuelTask extends Task {
	
	GasStation gasStation;
	Tile goal;

	public RefuelTask(Drone drone, Building gasStation, Tile tile) {
		super(drone);
		this.gasStation = (GasStation) gasStation;
		goal = tile;
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 3);
//		if(drone.getCurrentTile().getBuilding().equals(BuildingEnum.POWERPLANT)){
		if(drone.getCurrentTile() == goal){
			gasStation.addDrone(drone);
			if(drone.getGas() < drone.getMaxGas()){
				gasStation.refuel(drone);
				
				
				
				}else{
					drone.getTaskList().push(new RefuelTask(drone, gasStation, goal));
				}
			
		}else{
			
			drone.getTaskList().push(new RefuelTask(drone, gasStation, goal));
			drone.getTaskList().push(new MoveTask(drone, gasStation.getTileList().get(0), true));
			drone.getTaskList().peek().execute(map);
		}
	}
}
