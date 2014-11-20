package tiles;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public enum WeatherEnum {
	DAY ("", -1),
	NIGHT ("", 1),
	STORM ("#", 2),
	NOTHING ("", 0);
	
	private String textRepresentation;
	private int darkness;
	private Image image;
	
	WeatherEnum(String s, int n) {
		textRepresentation = s;
		darkness = n;
		try {
			image = ImageIO.read(new File("images/transparentplacehold.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTextRepresentation() {
		return textRepresentation;
	}
	
	public int getDarkness() {
		return darkness;
	}
	
	public Image getImage() {
		return image;
	}
}
