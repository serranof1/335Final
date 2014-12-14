package tiles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
/**
 * The GroundEnum stores each type of regular ground a {@link Tile} can have, as well as its
 * graphical and textual representation.
 * @author Team Rosetta
 *
 */
public enum GroundEnum {
	OCEAN ("~", 4, "ocean.png"),
	SAND (",", 1, "sand.png"),
	PLAIN ("_", 0, "plain.png"),
	MOUNTAIN ("M", 7, "mountain.png"),
	GRASS ("G", 0, "grass.png");
	
	private String textRepresentation;
	private int movementCost;
	private BufferedImage image;
	
	private GroundEnum(String s, int n, String fileName) {
		textRepresentation = s;
		movementCost = n;
		try {
			image = ImageIO.read(new File("images/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public Image getImage() {
		return image;
	}
}
