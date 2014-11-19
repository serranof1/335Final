package tiles;

public enum WeatherEnum {
	DAY ("", -1),
	NIGHT ("", 1),
	STORM ("#", 2),
	NOTHING ("", 0);
	
	private String textRepresentation;
	private int darkness;
	
	WeatherEnum(String s, int n) {
		textRepresentation = s;
		darkness = n;
	}
	
	public String getTextRepresentation() {
		return textRepresentation;
	}
	
	public int getDarkness() {
		return darkness;
	}
}
