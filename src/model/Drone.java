package model;

import game.Map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import pathfinding.Mover;
import task.ChargeTask;
import task.DeadTask;
import task.DefaultTask;
import task.MoveTask;
import task.RepairTask;
import task.Task;
import tiles.ResourceEnum;
import tiles.Tile;
import pathfinding.Path;

public class Drone implements Mover{

	private Path myPath;
	
	private int locationX;
	private int locationY;
	private boolean charging = false;
	private boolean repairing = false;
	private boolean filling = false;
	private String name;

	/**
	 * Variable keeps track of if a drone has died and can be consumed for
	 * resources
	 */
	private boolean reclaim = true;

	private Items currentItem;

	/**
	 * List of tiles that a drone has been assigned, with the first tile always
	 * being adjacent to the drone's current tile
	 */
	private LinkedList<Point> currentPath = new LinkedList<Point>();

	/**
	 * @author Cody Jensen Queue of task for drone to execute
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
	private double maxPower;

	/**
	 * Instance variable for the amount of materials a drone currently holds.
	 * Later we can implement a cap, and increase movement cost if the drone has
	 * more things.
	 */
	private double materials;

	/**
	 * Instance variable for the current tile the drone is on.
	 */
	protected Tile currentTile;
	
	/**
	 * Instance keeps track of the drones charging state; This may not be
	 * needed.
	 */
	// private boolean charging;

	/**
	 * Creates a drone with a set amount of starting power, and a default job of
	 * do nothing
	 * 
	 * @param power
	 *            The initial power that a drone starts with
	 * @param defaultJob
	 *            The Default job that a drone will always do
	 */

	private int movementAbility = 1;

	private int repair;
	private int maxRepair = 100;
	
	private int gas;
	private int maxGas;
	
	private int carbon = 0;
	private int iron = 0;
	private int methane = 0;
	private int maxCapacity = 100;

	public Drone(String name, double power, Tile start) {
		this.name = name;
		this.power = power;
		maxPower = 400;
		repair = 100;
		// if (this.power > maxPower) {
		// this.power = maxPower;
		// }
		currentTile = start;
		currentTile.setHasDrone(true);
		materials = 0;
		locationX = start.getX();
		locationY = start.getY();
		taskList.push(new DefaultTask(this));
		
		gas = 1000;
		maxGas = 1000;
	}

	// Getters and setters for location
	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int newX) {
		locationX = newX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int newY) {
		locationY = newY;
	}

	public LinkedList<Point> getPath() {
		return currentPath;
	}


	public void executeTaskList(Map map) {
		taskList.peek().execute(map);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double newPower) {
		if (newPower > maxPower) {
			power = maxPower;
		} else {
			power = newPower;
		}
	}

	public void setMaxPower(double newMax) {
		maxPower = newMax;
	}

	public double getMaxPower() {
		return maxPower;
	}

	public LinkedList<Task> getTaskList() {
		return taskList;
	}

	public void setCurrentTile(Tile input) {
		currentTile.setHasDrone(false);
		currentTile = input;
		locationX = currentTile.getX();
		locationY = currentTile.getY();
		currentTile.setHasDrone(true);

	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public void giveItem(Items item) {
		currentItem = item;
	}

	public Point getNextTile() {
		return currentPath.getFirst();
	}

	public void setPath(LinkedList<Point> newPath) {
		for (Point point : newPath) {
			currentPath.add(point);
		}
	}

	public void setMovementAbility(int i) {
		movementAbility = i;
	}

	public int getMovementAbility() {
		return movementAbility;
	}

	public boolean hasItem() {
		// TODO Auto-generated method stub
		if (currentItem != null) {
			return true;

		} else {
			return false;
		}
	}

	public boolean isFull() {
		return true;
	}

	public int getInventory() {
		return 10;
	}

	public int getRepair() {
		return repair;
	}

	public void setRepair(int i) {
		repair = i;
	}

	public int getMaxRepair() {
		return maxRepair;
	}

	public Path getMyPath() {
		return myPath;
	}

	public void setMyPath(Path myPath) {
		this.myPath = myPath;
	}
	
	public int getGas() {
		return gas;
	}
	
	public int getMaxGas() {
		return maxGas;
	}
	
	public void setGas(int g) {
		gas = g;
	}
	
	public void setMaxGas(int g) {
		maxGas = g;
	}

	public void setMaxRepair(int i) {
		maxRepair = i;
	}
	
	public int getIron() {
		return iron;
	}
	
	public void incrementIron(int i) {
		iron += i;
	}
	
	public int getCarbon() {
		return carbon;
	}
	
	public void incrementCarbon(int i) {
		carbon += i;
	}
	
	public int getMethane() {
		return methane;
	}
	
	public void incrementMethane(int i) {
		methane += i;
	}
	
	public void gather(int i, ResourceEnum resource) {
		if (iron + carbon + methane + i < maxCapacity) {
			if (resource == ResourceEnum.IRON) {
				iron += i;
			} else if (resource == ResourceEnum.CARBON) {
				carbon += i;
			} else if (resource == ResourceEnum.METHANE) {
				methane += i;
			}
		} else {
			if (resource == ResourceEnum.IRON) {
				iron = maxCapacity - carbon - methane;
			} else if (resource == ResourceEnum.CARBON) {
				carbon = maxCapacity - iron - methane;
			} else if (resource == ResourceEnum.METHANE) {
				methane = maxCapacity - iron - carbon;
			}
		}
	}
	
	public int depositResource(ResourceEnum resource) {
		if (resource == ResourceEnum.IRON) {
			int temp = iron;
			iron = 0;
			return temp;
		} else if (resource == ResourceEnum.CARBON) {
			int temp = carbon;
			carbon = 0;
			return temp;
		} else if (resource == ResourceEnum.METHANE) {
			int temp = methane;
			methane = 0;
			return temp;
		} else {
			return 0;
		}
	}
	
	public void toggleCharge(){
		charging = !charging;
	}
	public boolean isCharging(){
		return charging;
	}
	public void toggleRepair(){
		repairing = !repairing;
	}
	public boolean isRepairing(){
		return repairing;
	}
	public void toggleFilling(){
		filling = !filling;
	}
	public boolean isFilling(){
		return filling;
	}
}