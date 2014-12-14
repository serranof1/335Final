package task;

import buildings.Building;
import buildings.MethanePlant;
import buildings.SolarPlant;
import model.Drone;
import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;

/**
 * MethaneTask is a {@link Task} that assigns a {@link Drone} to find a {@link MethanePlant} and refuel.
 * @author Team Rosetta
 *
 */
public class MethaneTask extends Task{
	
	MethanePlant plant;
	Tile goal;
	
	public MethaneTask(Drone drone, Building methanePlant, Tile tile) {
		super(drone);
		this.plant = (MethanePlant) methanePlant;
		goal = tile;
		drone.toggleFilling();
		
	}
	/**
	 * @param map - The {@link Map} on which to execute. The {@link Drone}'s needs are decremented and
	 * the drone is refueld. If it is not at a {@link MethanePlant} it is assigned a {@link MoveTask}.
	 */
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
					drone.toggleFilling();
				}
			
		}else{
			
			drone.getTaskList().push(new MoveTask(drone, plant.getEmptyTile(), false));
			drone.getTaskList().peek().execute(map);
		}
	}
}
