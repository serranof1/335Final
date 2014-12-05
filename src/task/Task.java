package task;

import game.Map;

import java.util.List;

import model.Drone;
import tiles.Tile;


public abstract class Task {
	protected Drone drone;
	private Tile tile; 
	
	public abstract void execute(Map map);
	
	public Task(Drone drone){
		this.drone = drone;
	}
	
	public Drone getDrone(){
		return drone;
	}
}
