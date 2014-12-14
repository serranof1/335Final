package pathfinding;

/**
 * A tagging interface for an object representing the entity in the game that
 * is going to moving along the path. This allows us to pass around entity/state
 * information to determine whether a particular tile is blocked, or how much
 * cost to apply on a particular tile.
 * 
 * For instance, a Mover might represent a tank or plane on a game map. Passing round
 * this entity allows us to determine whether rough ground on a map should effect
 * the unit's cost for moving through the tile.
 * 
 * The original implementation of this pathfinding algorithm was written by Kevin Glass. It has
 * been adapted to function with our game.
 * 
 * @author Kevin Glass and Team Rosetta
 */
public interface Mover {

}