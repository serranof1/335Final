package tiles;

import java.awt.Image;

/**
 * BuildingTile is a layer of the {@link Tile} that stores the {@link Building} as a 
 * {@link BuildingEnum}
 * @author Team Rosetta
 *
 */
public class BuildingTile extends TileWrapper {
	
	private BuildingEnum building;
	
	public BuildingTile(BuildingEnum building) {
		this.building = building;
	}
	
	@Override
	public String drawTextForm() {
		return building.getTextRepresentation();
	}

	@Override
	public Image getImage() {
		return building.getImage();
	}
	
	public BuildingEnum getBuildingType() {
		return building;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
