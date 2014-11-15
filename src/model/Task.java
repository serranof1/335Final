package model;

import game.Tile;

import java.util.List;


public abstract class Task {
	private Drone drone;
	protected List<Tile> steps;
	private Tile tile; 
	
	public abstract void execute();
	
	
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
