package task;

import buildings.Building;
import model.Drone;
import resources.Carbon;
import tiles.ResourceEnum;
import tiles.Tile;
import game.Map;

/**
 * BuildTask is a {@link Task} in which a {@link Drone} is assigned a
 * {@link Building} to build.
 * 
 * @author Team Rosetta
 *
 */
public class BuildTask extends Task {

	private Tile buildLoc;
	private Building toBuild;
	private boolean flag = true;
	private int buildProgress;

	public BuildTask(Drone drone, Building build) {
		super(drone);
		toBuild = build;
		buildProgress = 0;
	}

	// We should probably set this up soon so that we can choose the location
	// the building is built.
	// Probably just change the constructor and such. We can do that once we can
	// get user input.
	@Override
	/**
	 * The execute method here decrements the {@link Drone}'s needs, as well as sets it to building. 
	 * If it is not in the correct location, it is assigned a {@link MoveTask}.
	 * @param map - This is a {@link Map} on which to perform the {@link Task}.
	 */
	public boolean execute(Map map) {
		drone.setGas(drone.getGas() - 5);
		drone.setPower(drone.getPower() - 10);
		drone.setRepair(drone.getRepair() - 1);
		int x = (int) toBuild.getLocation().getX();
		int y = (int) toBuild.getLocation().getY();
		buildLoc = map.getTile(y, x);
		if (map.getAllBuildings().get(0).getIron() > 1000) {
			if (drone.getCurrentTile() == buildLoc) {
				// buildProgress+=1;
				// if (toBuild.contruct()) {
				map.build(toBuild);
				toBuild.setFinished();
				drone.getTaskList().pop();
				map.getAllBuildings().get(0).setIron(-100);
				return true;
			// }
			} else {
				drone.getTaskList().push(new MoveTask(drone, buildLoc, true));
				drone.getTaskList().peek().execute(map);
			}
		}
		drone.getTaskList().pop();
		return false;
	}
}
