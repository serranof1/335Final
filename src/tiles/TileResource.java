package tiles;

import resources.Resource;

public abstract class TileResource extends TileWrapper {
	protected float chanceToLose;
	protected Resource resource;
	
	public TileResource(Resource resource, float chanceToLose) {
		this.resource = resource;
		this.chanceToLose = chanceToLose;
	}
	
	private float getChanceToLose() {
		return chanceToLose;
	}
	
	public Resource gather() {
		return resource;
	}
}
