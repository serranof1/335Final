package tiles;

import resources.Resource;

public class MethaneTile extends TileResource {
	
	public MethaneTile(Resource resource, float chanceToLose) {
		super(resource, chanceToLose);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String drawTextForm() {
		return "~";
	}

}
