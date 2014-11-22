package model;

import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;


public class ChargeTask extends Task{

	public ChargeTask(Drone drone ) {
		super(drone);
		
	}

	@Override
	public void execute(Map map) {
		if(nextToPower()){
			System.out.println("Drone is next to power Supply");
			if(drone.getPower()<100){
				drone.setPower(drone.getPower()+10);
				System.out.println("Incremented Charge by 10");
				if(drone.getPower()< 200){
					drone.getTaskList().push(new ChargeTask(drone));
				}else{
					System.out.println("Done Charging");
					drone.setPower(100);
				}
			}
		}else{
			System.out.println("Moving To power supply!");
			
			Tile chargingTile = map.getTile(10, 15);
			drone.getTaskList().push(new MoveTask(drone, chargingTile));
			drone.getTaskList().pop().execute(map);
		}
	}

	private boolean nextToPower() {
		if(drone.currentTile.getBuilding().equals(BuildingEnum.POWERPLANT))
			return true;
		else
			return false;
		//This return is just for testing purposes. It will always return true
		//return false;
	}

/*	private void moveToPower() {
		
		Tile chargingTile = map.getTile(10, 15);
		
		drone.getTaskList().push(new MoveTask(drone, chargingTile));
		
	}*/
}
