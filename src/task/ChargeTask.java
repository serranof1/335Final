package task;

import buildings.Building;
import buildings.SolarPlant;
import model.Drone;
import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;

/**
 * ChargeTask is a {@link Task} that assigns a {@link Drone} to find a {@link SolarPlant} and recharge.
 * @author Team Rosetta
 *
 */
public class ChargeTask extends Task{
	
	SolarPlant plant;
	Tile goal;
	
	public ChargeTask(Drone drone, Building plant, Tile tile ) {
		super(drone);
		this.plant = (SolarPlant) plant;
		goal = tile;
		drone.toggleCharge();
		
	}

	/**
	 * @param map - The {@link Map} on which to execute. The {@link Drone}'s needs are decremented and
	 * the drone is recharged. If it is not at a {@link SolarPlant} it is assigned a {@link MoveTask}.
	 */
	@Override
	public void execute(Map map) {
		drone.setGas(drone.getGas() - 2);
		drone.setRepair(drone.getRepair() -1);
		if(drone.getCurrentTile() == goal){
			plant.addDrone(drone);
			if(drone.getPower() < drone.getMaxPower()){
				plant.charge(drone);
				System.out.println("CHARGING");
				
				
				}else{
					drone.setPower(drone.getMaxPower());
					drone.getTaskList().pop();
					drone.toggleCharge();
				}
			
		}else{
			
			drone.getTaskList().push(new MoveTask(drone, plant.getEmptyTile(), false));
			drone.getTaskList().peek().execute(map);
		}
	}
}
