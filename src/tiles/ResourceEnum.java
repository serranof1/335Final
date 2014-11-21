package tiles;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum ResourceEnum {
	MINERAL (.1, "m", Color.ORANGE, "layerplaceholder.png"),
	METHANE (.1, "v", Color.YELLOW, "layerplaceholder.png"),
	GAS (.1, "g", Color.GREEN, "layerplaceholder.png"),
	IRON (.1, "i", Color.GRAY, "layerplaceholder.png"),
	CARBON (.1, "c", Color.RED, "layerplaceholder.png"),
	HYDROGEN (.1, "h", Color.BLUE, "layerplaceholder.png"),
	OXYGEN (.1, "o", Color.CYAN, "layerplaceholder.png"),
	DEADDRONE(0, "d", Color.DARK_GRAY, "layerplaceholder.png"),
	NOTHING (0, "", Color.BLACK, "layerplaceholder.png");
	
	private double lossChance;
	private String textRepresentation;
	private Color color;
	private BufferedImage image;
	
	ResourceEnum(double f, String s, Color c, String fileName) {
		lossChance = f;
		textRepresentation = s;
		color = c;
		try {
			image = ImageIO.read(new File("images/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public Image getImage() {
		return image;
	}
}
