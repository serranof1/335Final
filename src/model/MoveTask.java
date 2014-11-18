package model;

import game.Map;


public class MoveTask extends Task {

	public MoveTask(Drone drone) {
		super(drone);
	}

	@Override
	public void execute(Map map) {
	
		System.out.println("DRONE UPDATE");
		
		if(this.steps == null){
			//Find a new path here
		} else {
			//check if move is valid, if it is, move.  else generate new path and move
		}
	}

}
