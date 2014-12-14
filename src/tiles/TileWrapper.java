package tiles;

import java.awt.Image;

/**
 * TileWrapper represents each possible layer of a {@link Tile}. Each possible layer needs to be
 * able to draw its image representation as well as its string representation
 * @author Team Rosetta
 *
 */
public abstract class TileWrapper {
	public abstract String drawTextForm();
	public abstract Image getImage();
}
