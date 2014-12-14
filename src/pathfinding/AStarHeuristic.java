package pathfinding;

import game.Map;

/**
 * This interface implements a heuristic for the A* pathfinding algorithm.
 * @author Team Rosetta
 *
 */
public interface AStarHeuristic {
	/**
	 * Get the additional heuristic cost of the given tile. This controls the
	 * order in which tiles are searched while attempting to find a path to the 
	 * target location. The lower the cost the more likely the tile will
	 * be searched.
	 * 
	 * @param map The map on which the path is being found
	 * @param mover The entity that is moving along the path
	 * @param x The x coordinate of the tile being evaluated
	 * @param y The y coordinate of the tile being evaluated
	 * @param tx The x coordinate of the target location
	 * @param ty The y coordinate of the target location
	 * @return The cost associated with the given tile
	 */
	public float getCost(Map map, Mover mover, int x, int y, int tx, int ty);
}
