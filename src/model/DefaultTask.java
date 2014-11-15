package model;

import java.util.Random;

public class DefaultTask extends Task {
	
	Drone drone;

	public DefaultTask(Drone drone) {
		super(drone);
		this.drone = drone;
	}

	@Override
	public void execute() {
		/**
		 * Generate a random number between 1 and 4.
		 * if 1, move North, 2 move East, 3 move South, 4 move West
		 */
		Random random = new Random();
		int min = 1;
		int max = 5;
		int randomNumber = 1; 
				//random.nextInt(max - min) + min;
		//This will execute a move north, does not work yet
		/*if(randomNumber == 1 && !drone.currentTile.getNorth().getHasDrone()){
			drone.currentTile.setHasDrone(false);
			drone.currentTile.getNorth().setHasDrone(true);
			drone.currentTile = drone.currentTile.getNorth();
			System.out.println("DEFAULT TASK UPDATE");
			this.getDrone().endOfLoopUpdate();
		}*/
		
	}
	
	
}
