package task;

import buildings.Building;
import buildings.SolarPlant;
import model.Drone;
import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;


public class ChargeTask extends Task{
	
	SolarPlant plant;
	Tile goal;
	
	public ChargeTask(Drone drone, Building plant, Tile tile ) {
		super(drone);
		this.plant = (SolarPlant) plant;
		goal = tile;
		
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 3);
//		if(drone.getCurrentTile().getBuilding().equals(BuildingEnum.POWERPLANT)){
		if(drone.getCurrentTile() == goal){
			plant.addDrone(drone);
			if(drone.getPower() < drone.getMaxPower()){
				plant.charge(drone);
				
				
				
				}else{
					drone.getTaskList().push(new ChargeTask(drone, plant, goal));
				}
			
		}else{
			
			drone.getTaskList().push(new ChargeTask(drone, plant, goal));
			drone.getTaskList().push(new MoveTask(drone, plant.getTileList().get(0), true));
			drone.getTaskList().peek().execute(map);
		}
	}
}
