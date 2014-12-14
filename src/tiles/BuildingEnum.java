package tiles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
/**
 * The BuildingEnum represents each {@link Building} and stores their appropriate image and textual
 * representation
 * @author Team Rosetta
 *
 */
public enum BuildingEnum {
	NOTHING ("", "transparentplaceholder.png"),
	POWERPLANT ("P", "solarplant.png"),
	MINE ("X", "mine.png"), 
	BASE ("B", "base.png"),
	FARM ("F", "farm.png"),
	ENGINEERING("E", "engineering.png"),
	METHANEPLANT("MP", "methaneplant.png");

	
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
