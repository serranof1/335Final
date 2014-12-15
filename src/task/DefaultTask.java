package task;

import game.Map;

import java.util.Random;

import model.Drone;

/**
 * DefaultTask is a {@link Task} for {@link Drone}s idling.
 * @author Team Rosetta
 *
 */
public class DefaultTask extends Task {
	
	Drone drone;

	public DefaultTask(Drone drone) {
		super(drone);
		this.drone = drone;
	}
	
	/**
	 * This method, while the {@link Drone} is idling, has it randomly move around.
	 * @param map - The {@link Map} on which to act.
	 */
	@Override
	public boolean execute(Map map) {
		/**
		 * Generate a random number between 1 and 4.
		 * if 1, move North, 2 move East, 3 move South, 4 move West
		 */
		Random random = new Random();
		int min = 1;
		int max = 5;
		int randomNumber = random.nextInt(max - min) + min; 
		drone.setPower(drone.getPower() - 3);
		drone.setRepair(drone.getRepair() - 1);
		drone.setGas(drone.getGas() - 5);
		if(randomNumber == 1 && drone.getCurrentTile().getNorth().canMove()){
			drone.setCurrentTile(drone.getCurrentTile().getNorth());
		} else if(randomNumber == 2 && drone.getCurrentTile().getSouth().canMove()){
			drone.setCurrentTile(drone.getCurrentTile().getSouth());
		} else if(randomNumber == 3 &&  drone.getCurrentTile().getEast().canMove()){
			drone.setCurrentTile(drone.getCurrentTile().getEast());
		} else if (randomNumber == 4 && drone.getCurrentTile().getWest().canMove()) {
			drone.setCurrentTile(drone.getCurrentTile().getWest());
		} else {
			//do nothing if no tiles aroudn can be moved into
		}
		return false;
	}		
}
