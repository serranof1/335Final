package task;

import buildings.Building;
import buildings.MethanePlant;
import model.Drone;
import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;


public class MethaneTask extends Task{
	
	MethanePlant plant;
	Tile goal;
	
	public MethaneTask(Drone drone, Building methanePlant, Tile tile) {
		super(drone);
		this.plant = (MethanePlant) methanePlant;
		goal = tile;
		
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 2);
		if(drone.getCurrentTile() == goal){
			plant.addDrone(drone);
			if(drone.getGas() < drone.getMaxGas()){
				plant.fill(drone);
				System.out.println("REFUELING");
				

				}else{
					drone.setGas(drone.getMaxGas());
					drone.getTaskList().pop();
				}
			
		}else{
			
			drone.getTaskList().push(new MoveTask(drone, plant.getEmptyTile(), false));
			drone.getTaskList().peek().execute(map);
		}
	}
}
