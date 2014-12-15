package task;

import game.Map;
import model.Drone;
import tiles.ResourceEnum;
import tiles.ResourceTile;
import tiles.Tile;
import buildings.Base;
import buildings.Building;

/**
 * DepositTask is a {@link Task} in which a {@link Drone} deposits resources into a {@link Building}.
 * @author Team Rosetta
 *
 */
public class CollectResourcesTask extends Task {
	Tile resourceTile;
	Base base;
	private ResourceTile nothing = new ResourceTile(ResourceEnum.NOTHING);
	public CollectResourcesTask(Drone drone, Tile resourceTile, Base base) {
		super(drone);
		this.resourceTile = resourceTile;
		this.base = base;
		drone.toggleCollecting();
	}
	@Override
	public void execute(Map map) {
		drone.setPower(drone.getPower() - 3);
		drone.setGas(drone.getGas() - 1);
		drone.setRepair(drone.getRepair() - 1);
		if (drone.getCurrentTile() == resourceTile) {
			if(resourceTile.getResource().getResource() == ResourceEnum.CARBON){
				drone.gather(ResourceEnum.CARBON);
			}else{
				drone.gather(ResourceEnum.IRON);
			}
			resourceTile.setResource(nothing);
			
			
			drone.getTaskList().pop();
			drone.getTaskList().push(new DepositTask(drone, base));
		} else {
			System.out.println("COLLECT  TASK :    x:    " +resourceTile.getX() + "y:  " +resourceTile.getY());
			drone.getTaskList().push(new MoveTask(drone, resourceTile, false));
			drone.getTaskList().peek().execute(map);
		}
	}
}

