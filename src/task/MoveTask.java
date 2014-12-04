package task;

import java.awt.Point;

import model.Drone;
import model.Path;
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
		drone.setPower(drone.getPower() - 5);
		drone.setRepair(drone.getRepair() - 1);
		Tile current = drone.getCurrentTile();
		
		if(drone.getPath().isEmpty()){
			System.out.println("Drone doesn't have a path. Creating a new one");
			drone.setPath(map.findPath(current, goal, drone.getMovementAbility()));
//			drone.setPath(new Path(current, goal, drone.getMovementAbility()).getPath());
		}
		
		Point nextCoord = drone.getNextTile();
		//System.out.println("PATH:  " +drone.getNextTile());
		drone.setCurrentTile(map.getTile(nextCoord.x, nextCoord.y));
		drone.getPath().removeFirst();
		
		
	}
}
