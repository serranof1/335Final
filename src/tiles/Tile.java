package tiles;

import java.awt.Color;
import java.awt.Graphics;

import resources.Resource;

public class Tile {
	private TileWrapper[] tileStack = new TileWrapper[3];
	private boolean hasDrone;
	private Tile north, south, east, west;
	
	public Tile(TileBase base, TileResource resource, TileWeather weather) {
		tileStack[0] = base;
		tileStack[1] = resource;
		tileStack[2] = weather;
		hasDrone = false;
	}
	
	//Each tile consists of three objects: 
	//the base, such as ground or mountain
	//the resource, such as iron or noResource
	//the weather, such as sun or storm
	public void setBase(TileBase base) {
		tileStack[0] = base;
	}
	
	public String getBase(){
		return tileStack[0].drawTextForm();
		
	}
	public void setResource(TileResource resource) {
		tileStack[1] = resource;
	}
	
	public void setWeather(TileWeather weather) {
		tileStack[2] = weather;
	}
	
	//Occupied/not occupied methods
	public void setHasDrone(boolean hasDrone) {
		this.hasDrone = hasDrone;
	}
	
	public boolean getHasDrone() {
		return hasDrone;
	}

	//Tile nodes
	public Tile getNorth() {
		return north;
	}

	public void setNorth(Tile north) {
		this.north = north;
	}

	public Tile getSouth() {
		return south;
	}

	public void setSouth(Tile south) {
		this.south = south;
	}

	public Tile getEast() {
		return east;
	}

	public void setEast(Tile east) {
		this.east = east;
	}

	public Tile getWest() {
		return west;
	}

	public void setWest(Tile west) {
		this.west = west;
	}

	//Draw the tile
	public String drawTextForm() {
		if (hasDrone) {
			return "@";
		} else if (!(tileStack[2] instanceof NoWeather || tileStack[2] instanceof Sun)) {
			return tileStack[2].drawTextForm();
		} else if (!(tileStack[1] instanceof NoResource)) {
			return tileStack[1].drawTextForm();
		} else {
			return tileStack[0].drawTextForm();
		}
	}

	public void draw(Graphics g, int x, int y){
			
		g.fillRect(x, y, 50, 50);		
	}
	//Gather a resource
	public Resource gather() {
		return ((TileResource) tileStack[1]).gather();
	}
	//a boolean for if the tile has a resource, like in the other Tile class
	public boolean getResource() {
		return !(tileStack[1] instanceof NoResource);
	}
}