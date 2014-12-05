package task;

import game.Map;

import java.awt.Point;
import java.util.LinkedList;

import model.Drone;
import pathfinding.Path;
import pathfinding.Path.Step;
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
		if(drone.getCurrentTile() == goal){
			drone.getTaskList().pop();
			System.out.println("DRONE HAS ARRIVED AT DESTINATION");
			return;
		}
		
		System.out.println(drone.getMyPath() +"   newPath: " +newPath);
		if(drone.getMyPath() == null || newPath == true){
			System.out.println("Drone doesn't have a path. Creating a new one");
			//drone.setPath(map.findPath(current, goal));
			//drone.setPath(new Path(current, goal, drone.getMovementAbility()).getPath());
			map.clearVisited();
			Path path = map.getFinder().findPath(drone, drone.getLocationX(), drone.getLocationY(), goal.getX(), goal.getX());
			
			drone.setMyPath(map.getFinder().findPath(drone, drone.getLocationX(), drone.getLocationY(), goal.getX(), goal.getX()));
		}
		//Check next tile if available
		System.out.println("MoveTask path:  " +drone.getMyPath());
		
		Step nextStep = drone.getMyPath().getNextStep();
		
		if(map.getTile(nextStep.getX(), nextStep.getY()).canMove() == true){
			drone.setCurrentTile(map.getTile(nextStep.getX(), nextStep.getY()));
			drone.getMyPath().removeLast();
		} else {
			System.out.println("HITTING THE ELSE");
			drone.setMyPath(null);
			drone.getTaskList().pop();
			drone.getTaskList().push(new MoveTask(drone, goal, true));
			drone.getTaskList().peek().execute(map);
		}
		
		
		
		
	}
}
