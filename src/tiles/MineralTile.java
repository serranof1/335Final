package tiles;

import resources.Resource;

public class MineralTile extends TileResource {
	
	public MineralTile(Resource resource, float chanceToLose) {
		super(resource, chanceToLose);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String drawTextForm() {
		return "M";
	}

}
