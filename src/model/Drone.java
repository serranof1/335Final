package model;

import game.Tile;

import java.util.Queue;

public class Drone {
	
	private int locationX;
	private int locationY;
	
	/**
	 * @author Cody Jensen
	 * Queue of task for drone to execute
	 */
	private Queue<Task> taskList;
	
	/**
	 * Keeps track of the drone's current power level. Depletes over time by
	 * some factor (-.5?) when in standby and when doing tasks increases the
	 * factor (-1.0?) This will also be used for charging at the station(+.75?).
	 * Will set max power to be a certain number (100?) but can later be
	 * upgraded.
	 * 
	 */
	private double power;

	/**
	 * Instance variable for the amount of materials a drone currently holds.
	 * Later we can implement a cap, and increase movement cost if the drone
	 * 	has more things.
	 */
	private double materials;
	
	/**
	 * Instance variable for the current tile the drone is on.
	 */
	private Tile currentTile;
	
	/**
	 * Instance keeps track of the drones charging state;
	 */
	private boolean charging;
	
	/**
	 * Creates a drone with a set amount of starting power, and a default job of do nothing
	 * 
	 * @param power 	The initial power that a drone starts with
	 * @param defaultJob 	The Default job that a drone will always do
	 */

	public Drone(double power, Tile start) {
		this.power = power;
		currentTile = start;
		materials = 0;
		
		
	}
	
	//Getters and setters for location
	public int getLocationX(){
		return locationX;
	}
	
	public void setLocationX(int newX){
		locationX = newX;
	}
	
	public int getLocationY(){
		return locationY;
	}
	
	public void setLocationY(int newY){
		locationY = newY;
	}

	public boolean isCharging() {
		if (charging)
			return true;
		else
			return false;
	}

	/**
	 * This code will be executed depending on when the drones' power updates.
	 * It will either increment or decrement depending on different conditions.
	 * Other processes happen here as well, but that will be figured out later.
	 */
	public void endOfLoopUpdate() {
		if (charging)
			power += .75;
		else
			power--;
	}
}