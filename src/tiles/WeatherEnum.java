package tiles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum WeatherEnum {
	DAY ("", -1, "day.png"),
	NIGHT ("", 1, "night.png"),
	STORM ("#", 2, "storm.png"),
	NOTHING ("", 0, "genericweather.png");
	
	private String textRepresentation;
	private int darkness;
	private BufferedImage image;
	
	WeatherEnum(String s, int n, String fileName) {
		textRepresentation = s;
		darkness = n;
		try {
			image = ImageIO.read(new File("images/" + fileName));
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
