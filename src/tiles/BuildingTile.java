package tiles;

import java.awt.Image;


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
	
}
