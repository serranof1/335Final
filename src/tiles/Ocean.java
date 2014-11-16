package tiles;

public class Ocean extends TileBase {

	public Ocean() {
		movementCost = 2;
	}
	
	@Override
	public String drawTextForm() {
		return "~";
	}

}
