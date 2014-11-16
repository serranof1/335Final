package tiles;

import resources.Resource;

public class Tile2 {
	private TileWrapper[] tileStack = new TileWrapper[3];
	private boolean hasDrone;
	private Tile2 north, south, east, west;
	
	public Tile2(TileBase base, TileResource resource, TileWeather weather) {
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
	public Tile2 getNorth() {
		return north;
	}

	public void setNorth(Tile2 north) {
		this.north = north;
	}

	public Tile2 getSouth() {
		return south;
	}

	public void setSouth(Tile2 south) {
		this.south = south;
	}

	public Tile2 getEast() {
		return east;
	}

	public void setEast(Tile2 east) {
		this.east = east;
	}

	public Tile2 getWest() {
		return west;
	}

	public void setWest(Tile2 west) {
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

	//Gather a resource
	public Resource gather() {
		return ((TileResource) tileStack[1]).gather();
	}
	//a boolean for if the tile has a resource, like in the other Tile class
	public boolean getResource() {
		return !(tileStack[1] instanceof NoResource);
	}
}
