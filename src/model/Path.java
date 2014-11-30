package model;

import java.util.LinkedList;

import tiles.Tile;

public class Path {
	private LinkedList<Tile> path;
	private Tile destination;
	
	public Path(Tile currTile, Tile destTile, int dronePower) {
		// TODO Auto-generated constructor stub
		path = new LinkedList<Tile>();
		destination = destTile;
		findPath(currTile, dronePower);
	}
	
	public boolean findPath(Tile curr,  int dronePower) {
		
		LinkedList<Tile> canMoveTiles;
		if(dronePower > 0 && curr == destination)
			return true;
		else if(dronePower > 0){
			canMoveTiles = new LinkedList<Tile>();
			if(curr.getEast() != curr)
				canMoveTiles.push(curr.getEast());
			if(curr.getSouth() != curr)
				canMoveTiles.push(curr.getSouth());
			if(curr.getNorth() != curr)
				canMoveTiles.push(curr.getNorth());
			if(curr.getWest() != curr)
				canMoveTiles.push(curr.getWest());
			
			int currentInPath = path.size();
			for(Tile tile : canMoveTiles) {
				if(tile == destination) { return true; }
				if(tile.canMove()) {
					path.push(tile);
					if ( !findPath(tile, dronePower - tile.getGround().getMovementCost()) )
						path.pop();
					else
						return true;
				} else {
					canMoveTiles.pop();
				}
			}
			return false;
		} else 
			return false;
	}

}
