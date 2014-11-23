package model;

import game.Map;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import resources.Resource;
import tiles.*;

/**
 * This class contain the basic attributes for the buildings.
 * 
 * @author Yuanjun Ma
 *
 */

public abstract class Building{ 
	/*
	 * I think we don't need Building to extend TileWrapper; we should use the Building class
	 * for the LinkedList of Buildings that execute their duties each game loop; what gets put
	 * on the map can just be the BuildingTile that already exists, with the Enum format. (The
	 * enum is basically the textual representation and location on the map for the drones.)
	 * 
	 * Also, I am wondering why Building is not abstract, since SolarPlant extends it?
	 */
	
	private int buildCost;
	private Point location;
	private int width;
	private int height;
	private int life;
	private int inventory;
	private String buildingName;
	private Resource resource; 
	private List<Drone> droneList;
	private boolean finished;
	protected BuildingEnum typeOfBuilding;
	
	private final static int MAX_CAP = 4;

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
	 * @param source
	 *            a {@link String} represent the purpose of the building.
	 */
	public Building(int x, int y, int width, int length, Resource source, String name) {
		location = new Point(x, y);
		this.width = width;
		this.height = length;
		this.resource = source;
		buildingName = name;
		droneList = new ArrayList<Drone>();
		finished = false;
	}

	// Set the cost of the building
	protected void setCost(int price) {
		this.buildCost = price;
	}

	// Set the buildsite on the map
	/*
	protected void setBuildSite(Tile tile) {
		Tile curr = tile;
		Tile rowStart = null;
		
		for(int i = 0; i < width; i++) {
			if(curr.getSouth() == null) { return; }
			rowStart = curr.getSouth(); 
			for(int j = 0; j < height; j++) {
				tiles[i][j] = new TileBuilding();
				curr = curr.getEast();
			}
			curr = rowStart;
		}
	}*/
	//I'm not sure what the setBuildSite method is currently doing, but it had an error based on
	//the current implementation of tiles; I have commented it out, but otherwise left it, for now.
	
	// When drone need to recharges
	protected boolean addDrone(Drone drone) {
		if(droneList.size() == MAX_CAP)						// check the maximum capacity before add
			return false;
		droneList.add(drone);
		return true;
	}

	// Tell the building to collectResource
	protected boolean collectResource(int amount) {
		if (inventory >= 100)
			return false;
		
		inventory += amount;
		return true;
	}

	// Check the surrounding surface of the tile, make sure there are enough room for building
	public boolean canBuild(Tile startTile) {
		Tile curr = null;
		Tile rowStart = null;

		curr = startTile;
		for(int i = 0; i < width; i++) {
			if(curr.getGround() == null) { return false; }
			if (curr.getSouth() != null) {rowStart = curr.getSouth();} //The start of the next row is one south of the starting node.
			/*
			 * A diagram of the node behavior:
			 * 1 2 3
			 * 4 5 6
			 * 7 8 9
			 * 
			 * Starting at curr = 1, rowStart is set to 4
			 * Then, curr moves east 1 -> 2 -> 3 (ie, until we hit the end of the internal loop of length <required width>
			 * Next, curr is set to rowStart, ie, 4 and rowStart is set to south of curr, ie, 7.
			 * Then, curr moves east 4 -> 5 -> 6, repeating the process. This continues until the other loop of length <required height> finishes
			 */
			for(int j = 0; j < height; j++) {
				if(curr.getGround().getGround() == GroundEnum.PLAIN) { //Sorry about the bad naming here. Tile's getGround method gives a GroundTile whose getGround method gives the Ground enum, if it's PLAIN, it can build
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
	protected void build(int buildRate) {
		if (life <= 0 && life > 100)
			life += buildRate;
	}

	// Return the location of the building on the map
	public Point getLocation() {
		return location;
	}

	// Return the cost of resource to construct the building
	public int getCost() {
		return buildCost;
	}

	// Return the size of the building
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Resource getBuildingResource() {
		return resource;
	}
	
	// Return the informations of the building
	public String toString() {
		String info = "";
		info = info + buildingName + "\n";
		info = info + life + "\n";
		info = info + "This building collect " + resource + "\n";
		info = info + inventory + "\n";
		info = info + buildCost + "\n";
		
		return info;
	}
	
	public BuildingEnum getTypeOfBuilding() {
		return typeOfBuilding;
	}

	public void executeOnBuilding(Map map) {
		// TODO Auto-generated method stub
		
	}
	public void setFinished(){
		finished = true;
	}

	public boolean isFinished() {
		// TODO Auto-generated method stub
		return finished;
	}
}
