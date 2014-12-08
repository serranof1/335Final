package buildings;

import game.Map;

import java.awt.Point;
import java.util.ArrayList;

import model.Drone;
import tiles.BuildingEnum;
import tiles.GroundEnum;
import tiles.Tile;


public abstract class Building {

	private String buildingName;
	private BuildingEnum typeOfBuilding;

	private Point location;
	private int width;
	private int length;

	private int health;
	private int currentResources = 0;
	private int resourceCap;
	private int resourceCost;
	private int iron, carbon, electricity, methane;
	
	private boolean finished;


	private ArrayList<Drone> droneList;
	private ArrayList<Tile> tileList;

	public abstract void executeOnBuilding(Map map);

	/**
	 * Construct the Building object, the parameters present the location, size,
	 * and function of the building.
	 * 
	 * @param x
	 *            an {@link Integer} represent the location of the building on x
	 *            axis.
	 * @param y
	 *            an {@link Integer} represent the location of the building on y
	 *            axis.
	 * @param width
	 *            an {@link Integer} represent the width of the building on the
	 *            map.
	 * @param length
	 *            an {@link Integer} represent the length of the building on the
	 *            map.
	 */
	public Building(int locX, int locY, int wid, int len, int cap,
			String name, BuildingEnum type) {
		buildingName = name;
		typeOfBuilding = type;
		location = new Point(locX, locY);
		width = wid;
		length = len;

		health = 100;
		resourceCap = cap;
		finished = false;

		droneList = new ArrayList<Drone>(width * length);
		tileList = new ArrayList<Tile>(width * length);
	}

	public boolean addDrone(Drone drone) {

		for (int index = 0; index < droneList.size(); index++) {
			if (droneList.get(index) == null) {
				droneList.add(index, drone);
				tileList.get(index).setHasDrone(true);
				return true;
			}
		}
		return false;
	}
	
	public void removeDrone(Drone drone){
		for (int index = 0; index < droneList.size(); index++) {
			droneList.get(index).getName().equals(drone.getName());
			droneList.remove(index);
			tileList.get(index).setHasDrone(false);
			break;
		}
	}

	// Check the surrounding surface of the tile, make sure there are enough
	// room for building
	public boolean canBuild(Tile startTile) {

		Tile curr = null;
		Tile rowStart = null;

		curr = startTile;
		for (int i = 0; i < width; i++) {
			if (curr.getGround() == null) {
				return false;
			}
			if (curr.getSouth() != null) {
				rowStart = curr.getSouth();
			} // The start of the next row is one south of the starting node.
			/*
			 * A diagram of the node behavior: 1 2 3 4 5 6 7 8 9
			 * 
			 * Starting at curr = 1, rowStart is set to 4 Then, curr moves east
			 * 1 -> 2 -> 3 (ie, until we hit the end of the internal loop of
			 * length <required width> Next, curr is set to rowStart, ie, 4 and
			 * rowStart is set to south of curr, ie, 7. Then, curr moves east 4
			 * -> 5 -> 6, repeating the process. This continues until the other
			 * loop of length <required height> finishes
			 */

			for (int j = 0; j < length; j++) {
				if (curr.getGround().getGround() == GroundEnum.PLAIN
						|| curr.getGround().getGround() == GroundEnum.SAND) { 
					curr = curr.getEast();
				} else {
					return false;
				}
			}
			curr = rowStart;
		}
		return true;

	}

	// How long to build the building depends on the dimension of the building

	// Return the location of the building on the map
	public Point getLocation() {
		return location;
	}

	// Return the size of the building
	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	public BuildingEnum getTypeOfBuilding() {
		return typeOfBuilding;
	}

	public void setFinished() {
		finished = true;
	}

	public boolean isFinished() {
		return finished;
	}

	public void depositAll(int x) {
		System.out.println("Stuff has been deposited");
		currentResources = x;
	}

	public int getInventory() {
		return currentResources;
	}

	public void setInventory(int resourceCurr) {
		currentResources = resourceCurr;
	}

	public void addTile(Tile addTile) {
		tileList.add(addTile);
	}
	public ArrayList<Tile> getTileList(){
		return tileList;
	}
	
	public ArrayList<Drone> getDroneList(){
		return droneList;
	}

	public Tile getEmptyTile() {
		for (Tile tile : tileList) {
			if (tile.getHasDrone() == false) {
				return tile;
			}
		}
		return null;
	}
	
	public void setCarbon(int x){
		carbon = x;
	}
	public int getCarbon(){
		return carbon;
	}
	
	public void setIron(int x){
		iron = x;
	}
	public int getIron(){
		return iron;
	}
	
	public void setPower(int x){
		electricity = x;
	}
	public int getPower(){
		return electricity;
	}
	
	public void setMethane(int x){
		methane = x;
	}
	public int getMethane(){
		return methane;
	}
	public void setHealth(int i) {
		health = i;
	}
	public int getHealth() {
		return health;
	}
}
