package model;

import game.Map;

public class DeadTask extends Task{
	
	Drone drone;
	
	public DeadTask(Drone drone){
		super(drone);
		this.drone = drone;
	}

	@Override
	public void execute(Map map) {
		//The execute method does nothing here. We could use this part
		//to convert the tile to use as a resource
		System.out.println("Dead task being executed");
	}
}
