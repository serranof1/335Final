package task;

import game.Map;

import java.util.Random;

import model.Drone;

public class DefaultTask extends Task {
	
	Drone drone;

	public DefaultTask(Drone drone) {
		super(drone);
		this.drone = drone;
	}

	@Override
	public void execute(Map map) {
		/**
		 * Generate a random number between 1 and 4.
		 * if 1, move North, 2 move East, 3 move South, 4 move West
		 */
		Random random = new Random();
		int min = 1;
		int max = 5;
		int randomNumber = random.nextInt(max - min) + min; 
		drone.setPower(drone.getPower() - 5);
		drone.setRepair(drone.getRepair() - 1);
		if(randomNumber == 1){
			drone.setCurrentTile(drone.getCurrentTile().getNorth());
		} else if(randomNumber == 2){
			drone.setCurrentTile(drone.getCurrentTile().getSouth());
		} else if(randomNumber == 3){
			drone.setCurrentTile(drone.getCurrentTile().getEast());
		} else {
			drone.setCurrentTile(drone.getCurrentTile().getWest());
		}
	}
	
	
}
