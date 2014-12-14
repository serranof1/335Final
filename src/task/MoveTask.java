package task;

import game.Map;

import java.awt.Point;
import java.util.LinkedList;

import model.Drone;
import pathfinding.Path;
import pathfinding.Path.Step;
import tiles.Tile;

/**
 * MoveTask is a {@link Task} in which a {@link Drone} generates a new path to a location and moves there.
 * @author Team Rosetta
 *
 */
public class MoveTask extends Task {


	Tile goal;
	boolean newPath = false;;

	public MoveTask(Drone drone, Tile tile, boolean newPath) {
		super(drone);
		goal = tile;
		this.newPath = newPath;
	}
	
	/**
	 * The {@link Drone}'s needs are decremented and the {@link Drone} moves towards its goal {@link Tile}.
	 * If it is at the designated {@link Tile} the MoveTask ends.
	 * @param map - The {@link Map} on which to execute.
	 */
	@Override
	public void execute(Map map) {
		drone.setGas(drone.getGas() - 5);
		drone.setPower(drone.getPower() - 3);
		drone.setRepair(drone.getRepair() - 1);
		Tile current = drone.getCurrentTile();
		if(drone.getCurrentTile() == goal){
			drone.getTaskList().pop();
			System.out.println("DRONE HAS ARRIVED AT DESTINATION");
			return;
		}


		if(drone.getMyPath() == null || newPath == true){
			System.out.println("Drone doesn't have a path. Creating a new one");
			
			map.clearVisited();
			Path path = map.getFinder().findPath(drone, drone.getLocationX(), drone.getLocationY(), goal.getX(), goal.getY());			
			drone.setMyPath(path);
			if(path == null){
				return;
			}
			 
				
	}
	//Check next tile if available
	Step nextStep = drone.getMyPath().getNextStep();

	if(map.getTile(nextStep.getX(), nextStep.getY()).canMove() == true){
		drone.setCurrentTile(map.getTile(nextStep.getX(), nextStep.getY()));
		drone.getMyPath().removeLast();
	} else {
		System.out.println("movetask else: " +map.getTile(nextStep.getX(), nextStep.getY()).canMove());
		drone.setMyPath(null);
		drone.getTaskList().pop();
		/*
		if (goal.getHasDrone()) {
			goal = goal.getEast();
		}
		if (goal.getHasDrone()) {
			goal = goal.getSouth();
		}
		if (goal.getHasDrone()) {
			goal = goal.getWest();
		}*/
		drone.getTaskList().push(new MoveTask(drone, goal, false));
		drone.getTaskList().peek().execute(map);
	}




}
}
