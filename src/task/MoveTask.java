package task;

import game.Map;

import java.awt.Point;
import java.util.LinkedList;

import model.Drone;
import tiles.Tile;


public class MoveTask extends Task {

	
	Tile goal;
	boolean newPath = false;;
	
	public MoveTask(Drone drone, Tile tile, boolean newPath) {
		super(drone);
		goal = tile;
		this.newPath = newPath;
	}

	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 5);
		drone.setRepair(drone.getRepair() - 1);
		Tile current = drone.getCurrentTile();
		
		if(drone.getPath().isEmpty() || newPath == true){
			System.out.println("Drone doesn't have a path. Creating a new one");
			drone.setPath(map.findPath(current, goal));
//			drone.setPath(new Path(current, goal, drone.getMovementAbility()).getPath());
		}
		//Check next tile if available
	
		Point nextCoord = drone.getNextTile();
		if(map.getTile(nextCoord.x, nextCoord.y).canMove() == true){
			drone.setCurrentTile(map.getTile(nextCoord.x, nextCoord.y));
			drone.getPath().removeFirst();
		} else {
			drone.setPath(new LinkedList<Point>());
			drone.getTaskList().push(new MoveTask(drone, goal, true));
			drone.getTaskList().pop().execute(map);
		}
		
		
		
		
	}
}
