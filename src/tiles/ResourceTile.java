package tiles;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;
/**
 * The ResourceTile represents the layer of a {@link Tile} that holds resources.
 * @author Team Rosetta
 *
 */
public class ResourceTile extends TileWrapper {
	private ResourceEnum resource;
	
	public ResourceTile(ResourceEnum resource) {
		this.resource = resource;
	}
	
	public ResourceEnum getResource() {
		return resource;
	}
	
	public ResourceEnum gather() {
		if (resource.getLossChance() > new Random().nextDouble()) {
			resource = ResourceEnum.NOTHING;
		}
		return resource;
	}

	@Override
	public String drawTextForm() {
		return resource.getTextRepresentation();
	}
	
	public Color getColor() {
		return resource.getColor();
	}

	@Override
	public Image getImage() {
		return resource.getImage();
	}

	@Override
	public String toString() {
		
		return getResource().toString();
	}
}
