package tiles;

public abstract class TileBase extends TileWrapper {
	protected int movementCost;
	
	public int getMovementCost() {
		return movementCost;
	}
}
