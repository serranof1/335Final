package model;

import game.Map;

import java.util.Random;

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
		int randomNumber = random.nextInt(max - min) + min;; 
		//System.out.println(drone.toString() + " doing Default Task");
		
		if(randomNumber == 1){
			drone.setCurrentTile(drone.currentTile.getNorth());
		} else if(randomNumber == 2){
			drone.setCurrentTile(drone.currentTile.getSouth());
		} else if(randomNumber == 3){
			drone.setCurrentTile(drone.currentTile.getEast());
		} else {
			drone.setCurrentTile(drone.currentTile.getWest());
		}
		drone.getTaskList().push(new DefaultTask(drone));
	}
	
	
}
