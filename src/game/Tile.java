package game;

public class Tile {

	private boolean isResource, hasDrone;
	private Tile north, south, east, west;
	private int row, col;
	
	public Tile(int row ,int col) {
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

	public boolean getHasDrone() {
		return hasDrone;
	}

	public void setHasDrone(boolean hasDrone) {
		this.hasDrone = hasDrone;
	}

}
