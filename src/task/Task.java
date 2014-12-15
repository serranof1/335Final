package task;

import game.Map;

import java.io.Serializable;
import java.util.List;

import model.Drone;
import tiles.Tile;

/**
 * Task is the abstract class representing different behaviors and commands that are issuable to a {@link Drone}.
 * @author Team Rosetta
 *
 */
public abstract class Task implements Serializable{
	protected Drone drone;
	private Tile tile; 
	
	public abstract boolean execute(Map map);
	
	public Task(Drone drone){
		this.drone = drone;
	}
	
	public Drone getDrone(){
		return drone;
	}
}
