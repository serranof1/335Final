package tiles;

public enum GroundEnum {
	OCEAN ("~", 2),
	SAND (",", 1),
	PLAIN ("_", 0),
	MOUNTAIN ("M", 3);
	
	private String textRepresentation;
	private int movementCost;
	
	private GroundEnum(String s, int n) {
		textRepresentation = s;
		movementCost = n;
	}
	
	String getTextRepresentation() {
		return textRepresentation;
	}
	//It's a method like this that makes me hesitate to make the tiles enums.
	//Obiviously, resources, weather, buildings, etc. all need a text representation
	//but they couldn't all be the same enum, because a resourceTile obviously doesn't
	//have an associated movement cost and ground doesn't have a resource.
	//There's no abstract enum, obviously, so they couldn't extend something.
	
	int getMovementCost() {
		return movementCost;
	}
}
