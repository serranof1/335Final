package model;

import game.Map;

import java.util.LinkedList;

import tiles.Tile;

public class Drone {
	
	private int locationX;
	private int locationY;
	
	/**
	 * Variable keeps track of if a drone has died and can be consumed for resources
	 */
	private boolean reclaim = true;
	
	private Items currentItem;
	
	/**
	 * List of tiles that a drone has been assigned, with the first tile
	 * always being adjacent to the drone's current tile
	 */
	private LinkedList<Tile> currentPath = new LinkedList<Tile>();
	
	
	/**
	 * @author Cody Jensen
	 * Queue of task for drone to execute
	 */
	private LinkedList<Task> taskList = new LinkedList<Task>();
	
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
	protected Tile currentTile;
	
	/**
	 * Instance keeps track of the drones charging state;
	 * This may not be needed.
	 */
	//private boolean charging;
	
	/**
	 * Creates a drone with a set amount of starting power, and a default job of do nothing
	 * 
	 * @param power 	The initial power that a drone starts with
	 * @param defaultJob 	The Default job that a drone will always do
	 */

	public Drone(double power, Tile start) {
		this.power = power;
		currentTile = start;
		currentTile.setHasDrone(true);
		materials = 0;
		taskList.push(new DefaultTask(this));
		
		
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

//	public boolean isCharging() {
//		return charging;
//	}

	/**
	 * 
	 */
	public void executeTaskList(Map map){
		System.out.println(this.toString() + " Current Power: " + power);
		
		if(power > 30){
			taskList.pop().execute(map);
			
		}else if(power > 5){
			System.out.println("Insufficient power, need to recharge");
			taskList.push(new ChargeTask(this)); 
			taskList.pop().execute(map);
		}else{
			System.out.println(this.toString() + " has died and should be reclaimed");
			taskList.push(new DeadTask(this));
			taskList.peek().execute(map);
		}
		if(power>0){
			power -=5;
			if(power<0){
				power = 0;
			}
		}
	}

	public double getPower() {	
		return power;
	}

	public void setPower(double newPower) {
		power = newPower;
	}

	public LinkedList<Task> getTaskList() {
		return taskList;
	}

	public void move(Tile input) {
		currentTile.setHasDrone(false);
		currentTile = input;
		currentTile.setHasDrone(true);
		
	}

	public void setCurrentTile(Tile input) {
		currentTile.setHasDrone(false);
		currentTile = input;
		currentTile.setHasDrone(true);
		
	}
	
	public Tile getCurrentTile() {
		return currentTile;
	}
}