package model;

import resources.Carbon;
import tiles.Tile;
import game.Map;

public class BuildTask extends Task{
	

	private Tile buildLoc;
	private Building toBuild;

	public BuildTask(Drone drone, Building build) {
		super(drone);
		toBuild = build;
		System.out.println("New build task assigned");
	}


	@Override
	public void execute(Map map) {
		// TODO Auto-generated method stub
		buildLoc = map.getTile(10,15);
		if(true){
		//if(drone.getLocationX()== 10 && drone.getLocationY() == 15){
			map.build(new SolarPlant(10, 15, new Carbon(), buildLoc));
			toBuild.setFinished();
			System.out.println("Drone has built the building");
		}else{
			moveDroneToBuilding();
			drone.getTaskList().push(new BuildTask(drone, toBuild));
		}
	}

	private void moveDroneToBuilding() {
		// TODO Auto-generated method stub
		
	}

}
