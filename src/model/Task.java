package model;

import game.Tile;

import java.util.List;


public abstract class Task {
	private Drone drone;
	private List<Tile> steps;
	private Tile tile; 
	
	public abstract void execute(Drone current);
	
	public Task(Drone drone, List<Tile> steps, Tile tile){
		this.drone = drone;
		this.steps= steps;
		this.tile = tile;
	}
}
