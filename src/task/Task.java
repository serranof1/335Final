package task;

import game.Map;

import java.util.List;

import model.Drone;
import tiles.Tile;


public abstract class Task {
	protected Drone drone;
	protected List<Tile> steps;
	private Tile tile; 
	
	public abstract void execute(Map map);
	
	
	/**
	 * A task only needs a drone, as a drone knows its currentTile
	 * @param drone
	 * @param tile
	 */
	public Task(Drone drone){
		this.drone = drone;
	}
	
	public Drone getDrone(){
		return this.drone;
	}
}
