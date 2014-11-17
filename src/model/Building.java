package model;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

/**
 * This class contain the basic attributes for the buildings.
 * 
 * @author Yuanjun Ma
 *
 */

public abstract class Building implements Serializable {
	private int buildCost;
	private Point location;
	private Dimension dimension;
	private String resource;
	
	/**
	 * Construct the Building object, the parameters present the location, size, and function of the building.
	 * @param x an {@link Integer} represent the location of the building on x axis.
	 * @param y an {@link Integer} represent the location of the building on y axis.
	 * @param width an {@link Integer} represent the width of the building on the map.
	 * @param length an {@link Integer} represent the length of the building on the map.
	 * @param source a {@link String} represent the purpose of the building.
	 */
	public Building(int x, int y, int width, int length, String source) {
		location = new Point(x, y);
		dimension = new Dimension(width, length);
		resource = source;
	}
	
	// comment on cost
	public void setCost(int price) {
		this.buildCost = price;
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
	public Dimension getDimension() {
		return dimension;
	}
}
