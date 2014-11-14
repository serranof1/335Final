package game;

public class Terrain {

	private boolean isResource, hasDrone;
	private Terrain north, south, east, west;
	private int row, col;
	
	public Terrain(int row ,int col) {
		this.isResource = false;
	}

	public boolean getResource(){
		return this.isResource;
	}

	public void setResource(boolean input){
		this.isResource = input;
	}

	public String toString(){
		if(hasDrone){
			return "dDb";
		} else if(!isResource){
			return "R";
		} else { 
	return "T";
		}
	}
	public Terrain getNorth() {
		return north;
	}

	public void setNorth(Terrain north) {
		this.north = north;
	}

	public Terrain getSouth() {
		return south;
	}

	public void setSouth(Terrain south) {
		this.south = south;
	}

	public Terrain getEast() {
		return east;
	}

	public void setEast(Terrain east) {
		this.east = east;
	}

	public Terrain getWest() {
		return west;
	}

	public void setWest(Terrain west) {
		this.west = west;
	}

	public boolean getHasDrone() {
		return hasDrone;
	}

	public void setHasDrone(boolean hasDrone) {
		this.hasDrone = hasDrone;
	}

}
