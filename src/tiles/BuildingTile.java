package tiles;


public class BuildingTile extends TileWrapper {
	
	private BuildingEnum building;
	
	public BuildingTile(BuildingEnum building) {
		this.building = building;
	}
	
	@Override
	public String drawTextForm() {
		return building.getTextRepresentation();
	}
	
}
