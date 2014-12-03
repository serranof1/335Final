package model;

import java.awt.Point;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import tiles.Tile;

public class Path {
	private LinkedList<Tile> path;
	private LinkedList<Tile> secPath;
	private Tile destination;

	public Path(Tile currTile, Tile destTile, int dronePower) {
		// TODO Auto-generated constructor stub
		path = new LinkedList<Tile>();
		secPath = new LinkedList<Tile>();
		destination = destTile;
		path.add(currTile);
		secPath.add(currTile);
		findPath(currTile, currTile, dronePower);
	}

	private boolean findPath(Tile curr, Tile prev, int dronePower) {

		// LinkedList<Tile> canMoveTiles = new LinkedList<Tile>();
		Comparator<Tile> comparator = new DistantComparator();
		PriorityQueue<Tile> canMoveTiles = new PriorityQueue<Tile>(10,
				comparator);
		// canMoveTiles.add(destination);
		if (curr.getX() == destination.getX()
				&& curr.getY() == destination.getY()) {
			return true;
		} else if (dronePower > 0) {
			// canMoveTiles = new LinkedList<Tile>();
			if (curr.getEast() != prev && !path.contains(curr.getEast()))
				canMoveTiles.add(curr.getEast());
			if (curr.getSouth() != prev && !path.contains(curr.getSouth()))
				canMoveTiles.add(curr.getSouth());
			if (curr.getNorth() != prev && !path.contains(curr.getNorth()))
				canMoveTiles.add(curr.getNorth());
			if (curr.getWest() != prev && !path.contains(curr.getWest()))
				canMoveTiles.add(curr.getWest());

			// canMoveTiles.poll();
			// canMoveTiles.removeLast();
			System.out.println(">>>>>>>>>>>> " + destination.getX() + "     "
					+ destination.getY() + "	" + "drone power		" + dronePower);
			for (Tile tile : canMoveTiles) {
				System.out.println("---> " + tile.getX() + "     "
						+ tile.getY());
			}
			// int currentInPath = path.size();
			for (Tile tile : canMoveTiles) {

				if (tile.canMove()) {
//					int dr = Math.abs(destination.getX() - tile.getX());
//					int dc = Math.abs(destination.getY() - tile.getY());
					Tile temp = curr;

					path.add(tile);
					// System.out.println("--> " + tile.getX() + "===> " +
					// tile.getY());
					if (!findPath(tile, temp, (dronePower - tile.getGround()
							.getMovementCost()))
							&& !path.isEmpty()) {
						System.out.println("tile " + tile.getX() + tile.getY() + "is removed!");
						System.out.println("current tile is " + tile.getX() + tile.getY());
						path.remove(tile);
					} else {
						return true;
					}
				} else {
					System.out.println("The tile was " + tile.getGround().getGround());
					System.out.println("The tile has drone " + tile.getHasDrone());
				}
			}
			return false;
		} else
			return false;
	}

	public LinkedList<Point> getPath() {
		LinkedList<Point> pathPoint = new LinkedList<Point>();
		LinkedList<Tile> somePath = path.isEmpty() ? secPath : path;

		for (Tile tile : somePath) {
			pathPoint.add(new Point(tile.getX(), tile.getY()));
		}
		return pathPoint;
	}

	private class DistantComparator implements Comparator<Tile> {

		@Override
		public int compare(Tile o1, Tile o2) {
			// TODO Auto-generated method stub
			int result1 = Math.abs(destination.getX() - o1.getX())
					+ Math.abs(destination.getY() - o1.getY());
			int result2 = Math.abs(destination.getX() - o2.getX())
					+ Math.abs(destination.getY() - o2.getY());
			return result1 - result2;
		}
	}
}
