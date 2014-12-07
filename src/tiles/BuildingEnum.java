package tiles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum BuildingEnum {
	NOTHING ("", "transparentplaceholder.png"),
	POWERPLANT ("P", "genericbuilding.png"),
	MINE ("X", "genericbuilding.png"), 
	BASE ("B", "genericbuilding.png"),
	FARM ("F", "genericbuilding.png"),
	REPAIR ("R", "genericbuilding.png"),
	GASSTATION ("G", "genericbuilding.png"),
	ENGINEERING("E", "genericbuilding.png");
	
	private String textRepresentation;
	private BufferedImage image;
	
	BuildingEnum(String s, String fileName) {
		textRepresentation = s;
		try {
			image = ImageIO.read(new File("images/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTextRepresentation() {
		return textRepresentation;
	}
	
	public Image getImage() {
		return image;
	}
}
