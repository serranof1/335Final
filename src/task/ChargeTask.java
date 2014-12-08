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
		drone.setGas(drone.getGas() - 2);
		drone.setRepair(drone.getRepair() -1);
//		if(drone.getCurrentTile().getBuilding().equals(BuildingEnum.POWERPLANT)){
		if(drone.getCurrentTile() == goal){
			plant.addDrone(drone);
			if(drone.getPower() < drone.getMaxPower()){
				plant.charge(drone);
				System.out.println("CHARGING");
				
				
				}else{
					drone.getTaskList().push(new ChargeTask(drone, plant, goal));
				}
			
		}else{
			
			drone.getTaskList().push(new ChargeTask(drone, plant, goal));
			drone.getTaskList().push(new MoveTask(drone, plant.getEmptyTile(), false));
			drone.getTaskList().peek().execute(map);
		}
	}
}
