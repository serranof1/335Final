package task;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import tiles.ResourceEnum;
import tiles.ResourceTile;
import model.Drone;
import game.Map;

/**
 * DeadTask is a {@link Task} that removes a {@link Drone} from all the acting {@link Drone}s.
 * @author Team Rosetta
 *
 */
public class DeadTask extends Task{
	
	Drone drone;
	private BufferedImage deadDrone;
	private ResourceTile iron = new ResourceTile(ResourceEnum.IRON);
	
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
	public boolean execute(Map map) {
		//The execute method does nothing here. We could use this part
		//to convert the tile to use as a resource
		drone.getCurrentTile().setImage(deadDrone);
		drone.getCurrentTile().setResource(iron);
		System.out.println("The drone is no more. It has ceased to be. It is an ex-drone. Nah, he's just pining for the fjords.");
		//System.out.println("Dead task being executed");
		return false;
	}
}
