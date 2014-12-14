package tiles;

import java.awt.Image;
/**
 * The GroundTile represents the layer in a {@link Tile} that determines things like movement.
 * It stores its type of GroundTile as a (@link GroundEnum}.
 * @author Gateway
 *
 */

public class GroundTile extends TileWrapper {
	
	private GroundEnum ground;
	
	public GroundTile(GroundEnum ground) {
		this.ground = ground;
	}
	
	public int getMovementCost() {
		return ground.getMovementCost();
	}
	@Override
	public String drawTextForm() {
		return ground.getTextRepresentation();
	}

	public GroundEnum getGround() {
		return ground;
	}

	@Override
	public Image getImage() {
		return ground.getImage();
	}
}
