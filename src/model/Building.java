package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import resources.Resource;
import tiles.Tile;
import tiles.TileBuilding;
import tiles.TileWrapper;

/**
 * This class contain the basic attributes for the buildings.
 * 
 * @author Yuanjun Ma
 *
 */

public class Building extends TileWrapper {
	
	private int buildCost;
	private Point location;
	private int width;
	private int height;
	private int life;
	private int inventory;
	private String buildingName;
	private Resource resource;
	private List<Drone> droneList;
	private TileWrapper[][] tiles;
	
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
	}

	// Set the cost of the building
	protected void setCost(int price) {
		this.buildCost = price;
	}

	// Set the buildsite on the map
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
	}
	
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
		String currBase = startTile.getBase();

		curr = startTile;
		for(int i = 0; i < width; i++) {
			if(curr.getSouth() == null) { return false; }
			for(int j = 0; j < height; j++) {
				if(currBase.compareTo("_") == 0) {
					curr = curr.getEast();
					currBase = curr.getBase();
				} else 
					return false;
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
	
	// Return true if the building is complete built
	public boolean isFinishBuilt() {
		if (life >= 100)
			return true;

		return false;
	}

	@Override
	public String drawTextForm() {
		// TODO Auto-generated method stub
		return buildingName;
	}
	
	// Return the informations of the building
	public String toString() {
		String info = null;
		info = info + buildingName + "\n";
		info = info + life + "\n";
		info = info + "This building collect " + resource + "\n";
		info = info + inventory + "\n";
		info = info + buildCost + "\n";
		
		return info;
	}
}
