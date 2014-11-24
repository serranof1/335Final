package model;

import resources.Carbon;
import tiles.Tile;
import game.Map;

public class BuildTask extends Task{
	

	private Tile buildLoc;
	private Building toBuild;
	private boolean flag = true;

	public BuildTask(Drone drone, Building build) {
		super(drone);
		toBuild = build;
	}

	//We should probably set this up soon so that we can choose the location the building is built.
	//Probably just change the constructor and such. We can do that once we can get user input.
	@Override
	public void execute(Map map) {
		// TODO Auto-generated method stub
		buildLoc = map.getTile(10,15);
		
		/*if(!toBuild.isFinished()){
			if(drone.getLocationX()  == buildLoc.getX() && drone.getLocationY() == buildLoc.getY()) {
				map.build(toBuild);
				toBuild.setFinished();
				System.out.println("Drone has built the building");
			} else {
				drone.getTaskList().push(new MoveTask(drone, buildLoc));
				System.out.println("Moving to building");
			}
		}*/
		
		if (drone.getCurrentTile() == buildLoc) {
			if (!toBuild.isFinished()) {
				map.build(toBuild);
				toBuild.setFinished();
				System.out.println("Drone has built the building");
			} else {
				//drone.getTaskList().push(new BuildTask(drone, toBuild));
				System.out.println("Test");
			}
		} else {
			drone.getTaskList().push(new MoveTask(drone, buildLoc));
			drone.getTaskList().pop().execute(map);
		}
	}

	private void moveDroneToBuilding() {
		drone.getTaskList().push(new MoveTask(drone, buildLoc));
	}

}
