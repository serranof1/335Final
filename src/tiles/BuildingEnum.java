package tiles;

public enum BuildingEnum {
	NOTHING (""),
	POWERPLANT ("P"),
	MINE ("X");
	
	private String textRepresentation;
	
	BuildingEnum(String s) {
		textRepresentation = s;
	}
	
	public String getTextRepresentation() {
		return textRepresentation;
	}
}
