package tiles;

import java.awt.Color;

public enum ResourceEnum {
	MINERAL (.1, "m", Color.ORANGE),
	METHANE (.1, "v", Color.YELLOW),
	GAS (.1, "g", Color.GREEN),
	IRON (.1, "i", Color.GRAY),
	CARBON (.1, "c", Color.RED),
	HYDROGEN (.1, "h", Color.BLUE),
	OXYGEN (.1, "o", Color.CYAN),
	NOTHING (0, "", Color.BLACK);
	
	private double lossChance;
	private String textRepresentation;
	private Color color;
	
	ResourceEnum(double f, String s, Color c) {
		lossChance = f;
		textRepresentation = s;
		color = c;
	}
	
	public double getLossChance() {
		return lossChance;
	}
	
	public String getTextRepresentation() {
		return textRepresentation;
	}
	
	public Color getColor() {
		return color;
	}
}
