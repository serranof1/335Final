package model;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import resources.Resource;
import tiles.Ground;
import tiles.TileBase;
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
	private int energyPool;
	private int inventory;
	private Resource resource;
	private List<Drone> droneList;
	private final static int MAX_DRONE = 4;
	private final static int BUILD_RATE = 20;
	
	/**
	 * Construct the Building object, the parameters present the location, size, and function of the building.
	 * @param x an {@link Integer} represent the location of the building on x axis.
	 * @param y an {@link Integer} represent the location of the building on y axis.
	 * @param width an {@link Integer} represent the width of the building on the map.
	 * @param length an {@link Integer} represent the length of the building on the map.
	 * @param source a {@link String} represent the purpose of the building.
	 */
	public Building(int x, int y, int width, int length, Resource source) {
		location = new Point(x, y);
		this.width = width;
		this.height = length;
		this.resource = source;
		droneList = new ArrayList<Drone>();
	}
	
	// 
	public void setCost(int price) {
		this.buildCost = price;
	}
	
	//
	public void addDrone(Drone drone) {
		droneList.add(drone);
	}
	
	public boolean collectResource() {
		if(inventory <= 100)
			return false;
		else
			inventory += 5;
		
		return true;
	}
	
	// 
	public boolean canBuild(TileBase tile) {
		TileBase curr = null;
		
		if(tile instanceof Ground) {
			curr = tile;
			while(curr instanceof Ground) {
				return true;
			}
		}
		return false;
	}
	
	// 
	public void getBuild(int buildRate) {
		if(life <= 0 && life > 100)
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
	
	public boolean finishBuilt() {
		if(life >= 100 )
			return true;
		
		return false;
	}
	
	@Override
	public String drawTextForm() {
		// TODO Auto-generated method stub
		return "";
	}
}
