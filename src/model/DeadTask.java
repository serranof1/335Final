package model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import game.Map;

public class DeadTask extends Task{
	
	Drone drone;
	private BufferedImage deadDrone;
	
	public DeadTask(Drone drone){
		super(drone);
		this.drone = drone;
		try {
			deadDrone = ImageIO.read(new File("images/deaddrone.png"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No drone image");
		}
	}

	@Override
	public void execute(Map map) {
		//The execute method does nothing here. We could use this part
		//to convert the tile to use as a resource
		drone.getCurrentTile().setImage(deadDrone);
		System.out.println("Dead task being executed");
	}
}
