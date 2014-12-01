package task;

import model.Drone;
import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;


public class ChargeTask extends Task{

	public ChargeTask(Drone drone ) {
		super(drone);
		
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 3);
//		if(drone.getCurrentTile().equals(BuildingEnum
		if(drone.getCurrentTile() == map.getTile(10, 15)){
			//System.out.println("Drone is next to power Supply");
			if(drone.getPower() < drone.getMaxPower()){
				drone.setPower(drone.getPower()+50);
				//System.out.println("Incremented Charge by 50");
				//We shouldn't need any code like this with the way maxPower is handled
				//if(drone.getPower()> drone.getMaxPower()){
				//	System.out.println("Done Charging");
				//	drone.setPower(400);
				}else{
					drone.getTaskList().push(new ChargeTask(drone));
				}
			
		}else{
			
			Tile chargingTile = map.getTile(10, 15);
			//drone.getTaskList().push(new ChargeTask(drone));
			drone.getTaskList().push(new MoveTask(drone, chargingTile));
			drone.getTaskList().pop().execute(map);
			//System.out.println("Moving To power supply!");
		}
	}

	private boolean nextToPower() {
		//System.out.println("POOWER PLANT:  " +drone.currentTile.getBuilding().equals(BuildingEnum.POWERPLANT));
		if(drone.getCurrentTile().getBuilding().equals(BuildingEnum.POWERPLANT))
			return true;
		else
			return false;
	}

/*	private void moveToPower() {
		
		Tile chargingTile = map.getTile(10, 15);
		
		drone.getTaskList().push(new MoveTask(drone, chargingTile));
		
	}*/
}
