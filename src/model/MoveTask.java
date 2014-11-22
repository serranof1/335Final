package model;

import java.awt.Point;

import game.Map;
import tiles.Tile;


public class MoveTask extends Task {

	
	Tile goal;
	
	public MoveTask(Drone drone, Tile tile) {
		super(drone);
		goal = tile;
	}

	@Override
	public void execute(Map map) {
		if(drone.getPath().isEmpty()){
			System.out.println("TEST TEST TEST");
			Tile current = drone.getCurrentTile();
			drone.setPath(map.findPath(current, goal));
			return;
			//System.out.println(map.findPath(current, goal).toString());
		}
		Point nextCoord = drone.getNextTile();
		drone.setCurrentTile(map.getTile(nextCoord.x, nextCoord.y));
		drone.getPath().removeFirst();
		
	}
}
