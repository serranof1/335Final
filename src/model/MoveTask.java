package model;

import java.awt.Point;

import game.Map;
import tiles.BuildingEnum;
import tiles.Tile;


public class MoveTask extends Task {

	
	Tile goal;
	
	public MoveTask(Drone drone, Tile tile) {
		super(drone);
		goal = tile;
	}

	@Override
	public void execute(Map map) {
		
		Tile current = drone.getCurrentTile();
		
		if(drone.getPath().isEmpty()){
			System.out.println("Drone doesn't have a path. Creating a new one");
			drone.setPath(map.findPath(current, goal));
		}
		
		Point nextCoord = drone.getNextTile();
		System.out.println("PATH:  " +drone.getNextTile());
		drone.setCurrentTile(map.getTile(nextCoord.x, nextCoord.y));
		drone.getPath().removeFirst();
		
		
		
//		if(!drone.currentTile.getBuilding().equals(BuildingEnum.POWERPLANT)){
//			drone.getTaskList().push(new MoveTask(drone, goal));
//		}
		
	}
}
