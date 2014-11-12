package model;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

public abstract class Building implements Serializable {
	private int cost;
	private Point location;
	private Dimension dimension;
	private String resource;
	
	public Building(int x, int y, int width, int height, String source) {
		location = new Point(x, y);
		dimension = new Dimension(width, height);
		resource = source;
	}
	
	public void setCost(int price) {
		this.cost = price;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public int getCost() {
		return cost;
	}
}
