package task;

import buildings.Building;
import buildings.MethPlant;
import model.Drone;
import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;


public class MethaneTask extends Task{
	
	MethPlant plant;
	Tile goal;
	
	public MethaneTask(Drone drone, Building methPlant, Tile tile ) {
		super(drone);
		this.plant = (MethPlant) methPlant;
		goal = tile;
		
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 2);
		if(drone.getCurrentTile() == goal){
			plant.addDrone(drone);
			if(drone.getGas() < drone.getMaxPower()){
				plant.cook(drone);
				
				
				
				}else{
					drone.getTaskList().push(new MethaneTask(drone, plant, goal));
				}
			
		}else{
			
			drone.getTaskList().push(new MethaneTask(drone, plant, goal));
			drone.getTaskList().push(new MoveTask(drone, plant.getTileList().get(0), true));
			drone.getTaskList().peek().execute(map);
		}
	}
}
