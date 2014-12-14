package buildings;

import game.Map;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import model.Drone;
import tiles.BuildingEnum;
import tiles.GroundEnum;
import tiles.Tile;

/**
 * A Building is an abstract class representing any static object we can place on the {@link Map}. In
 * particular, it has a size and location, often as well as a function it performs each game loop.
 * 
 * @author Team Rosetta
 *
 */
public abstract class Building extends Observable implements Serializable {

	private String buildingName;
	private BuildingEnum typeOfBuilding;

	private Point location;
	protected int width;
	protected int length;

	private int health;
	private int currentResources = 0;
	private int resourceCap;
	private int resourceCost;
	protected int iron;
	protected int carbon;
	private int electricity;
	private int methane;
	
	private boolean finished, inProgress;


	private ArrayList<Drone> droneList;
	protected ArrayList<Tile> tileList;
	
	/**
	 * Each Building has actions needing to be performed each game loop. These are performed in the
	 * executeOnBuilding method.
	 * 
	 * @param map - Most functions affect or need the {@link Map}.
	 */

	public abstract void executeOnBuilding(Map map);

	/**
	 * 
	 * @param locX - The x location of the top left of the Building on the {@link Map}
	 * @param locY - The y location of the top left of the Building on the {@link Map}
	 * @param wid - The width of the Building.
	 * @param len - The length (or height) of the Building.
	 * @param cap - The maximum amount of any given {@link Resource} that can be held by the Building.
	 * @param name - The name of the Building.
	 * @param type - Each building has a {@link BuildingEnum} to which it is tied in order to best interact
	 * with the {@link Tile} and {@link Map}.
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
	
	/**
	 * This method fairly evidently adds a {@link Drone} to the Building. Oftentimes, a {@link Drone} needs
	 * to be inside the building for some function, such as charging. This helps track that.
	 * 
	 * @param drone - The {@link Drone} to be added to the list of {@link Drone}s currently in the Building.
	 * @return boolean - True if a {@link Drone} is successfully added, false otherwise.
	 */
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
	
	/**
	 * As per the addDrone method, a method is needed to subsequently remove a {@link Drone}
	 * 
	 * @param drone - The {@link Drone} to be removed for the list of {@link Drone}s.
	 */
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
	/**
	 * This method checks the {@link Map} to ensure each {@link Tile} in the appropriate size of the
	 * Building can be built upon, ie, is not mountainous or waterlogged.
	 * @param startTile - The top left {@link Tile} on which will be built.
	 * @return boolean - True if no {@link Tile} checked has any property prohibiting building, false otherwise.
	 */
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
	
	public void setProgress() {
		inProgress = true;
	}

	public boolean inProgress() {
		return inProgress;
	}

	@Deprecated
	public void depositAll(int x) {
		System.out.println("Stuff has been deposited");
		currentResources = x;
		notifyObservers();
		setChanged();
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
	
	/**
	 * This method checks to see if any {@link Tile} making up the Building is empty, ie, does not
	 * have a {@link Drone}. This allows a space for new {@link Drone}s to be placed.
	 * @return Tile - The first empty {@link Tile}.
	 */
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
		notifyObservers();
		setChanged();
	}
	public int getCarbon(){
		return carbon;
	}
	
	public void setIron(int x){
		iron = x;
		notifyObservers();
		setChanged();
	}
	public int getIron(){
		return iron;
	}
	
	public void setPower(int x){
		electricity = x;
		notifyObservers();
		setChanged();
	}
	public int getPower(){
		return electricity;
	}
	
	public void setMethane(int x){
		methane = x;
		notifyObservers();
		setChanged();
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
