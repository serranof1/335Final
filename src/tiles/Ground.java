package tiles;

public class Ground extends TileBase {
	
	public Ground() {
		movementCost = 1;
	}
	
	@Override
	public String drawTextForm() {
		return "_";
	}

}
