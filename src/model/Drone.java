package model;

import java.util.LinkedList;

public class Drone {
	
	/**
	 * Private instance variable that will be use to determine if the drone is
	 * currently doing anything. The default will be the doNothing command
	 */
	private Command currentJob;
	
	/**
	 * Private linked list to keep track of the specific drones commands. As the
	 * drone gets more and more things to do if it needs power it will start
	 * moving further and further up the list until it is first then it will
	 * stop what its doing and go get power.
	 */
	private LinkedList<Command> taskList;
	
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
	 * Keeps track of whether the drone is charging
	 */
	private boolean charging;
	
	/**
	 * Instance variable that holds location of drone on the map
	 */
	private int locationX;
	private int locationY;
	
	/**
	 * Instance variable for the capacity amount of weight a drone currently holds.
	 * Later we can implement a cap, and increase movement cost if the drone
	 * 	has more things.
	 */
	private double inventory;

	
	/**
	 * Creates a drone with a set amount of starting power, and a default job of do nothing
	 * 
	 * @param power 	The initial power that a drone starts with
	 * @param defaultJob 	The Default job that a drone will always do
	 */

	public Drone(double power, Command defaultJob) {
		this.power = power;
		this.currentJob = defaultJob;
		this.charging = false;
		this.locationX = 0; 
		this.locationY = 0;			//This should be set to wherever the drones are made
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

	public void doNothing() {
		// TODO Auto-generated method stub
	}

//	private Comparator<Tile> tileSorter = new Comparator<Tile>() {
//
//		public int compare(Tile tile0, Tile tile1) {
//			if (n1.fCost < n0.fCost)
//				return 1;
//			if (n1.fcost > n0.fCost)
//				return -1;
//			return 0;
//		}
//	};
//	
//	
//  The beginnings of the A* Algorithm
//	public List<Tile> findPath(Vector21 start, Vector21 goal){
//		List<Tile> openList = new ArrayList<Tile>();
//		List<Tile> closedList = new ArrayList<Tile>();
//		Tile current = new Tile(start, null, 0, getDistance(start, goal));
//		openList.add(current);
//		while(openList.size() > 0){
//			Collections.sort(openList, tileSorter);
//			current = openList.get(0);
//			if(current.equals(goal)){
//				//return...
//			}
//			openList.remove(current);
//			closedList.add(current);
//			
//			for(int i = 0; i<9; i++){
//				if(i == 4) continue;
//				int x = current.getx();
//				int y = current.getY();
//				int xi = (i % 3) - 1;
//				int yi = (i / 3) - 1;
//				Tile at = getTile(x + xi, y+yi);
//				if(at = null) continue;
//				if(at.solid()) continue;
//				Vector2i a = new Vector2i(x+xi, y+yi);
//				double gCost = current.gCost + getDistance(current, a);
//				
//			}
//		}
//	}
//
//	private double getDistance(Vector2i tile, Vector21 goal) {
//		double dx = tile.getX() - goal.getX();
//		double dy = tile.getY() - goal.getY();
//		return Math.sqrt(dx * dx + dy * dy);
//	}

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