package tiles;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public enum BuildingEnum {
	NOTHING (""),
	POWERPLANT ("P"),
	MINE ("X"), 
	BASE ("B");
	
	private String textRepresentation;
	private Image image;
	
	BuildingEnum(String s) {
		textRepresentation = s;
		try {
			image = ImageIO.read(new File("images/transparentplaceholder.png"));
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
