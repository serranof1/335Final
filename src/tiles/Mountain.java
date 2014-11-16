package tiles;

public class Mountain extends TileBase {
	
	public Mountain() {
		movementCost = 3;
	}
	
	@Override
	public String drawTextForm() {
		return "m";
	}
}
