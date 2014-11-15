package game;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import javax.swing.JFrame;

import model.Drone;
import view.mapView;


public class MainGame extends JFrame{


	private static Drone drone1;
	private mapView mapView;
	private static Map map;
	private static List<Drone> droneList = new LinkedList<Drone>();
	private static LinkedList<Tile> resourceList;

	private boolean running = true;
	private boolean paused = false;
	private int fps = 60;
	private int frameCount = 0;
	private Timer timer;

	public static void main(String[] args)
	{
		MainGame game = new MainGame();
		initializeDrones();	
		game.setVisible(true);

	}
	/**
	 * @author Cody Jensen
	 * 
	 * This is where the starting groups of drones will be added, later a method to add a drone to the map can be added to replace this
	 */
	private static void initializeDrones() {
		Tile start = map.getTile(5,5);

		start.setHasDrone(true);

		drone1 = new Drone(200.0, start);
		drone1.setLocationX(5);
		drone1.setLocationY(5);
		droneList.add(drone1);
	}

	/**
	 * @author Cody Jensen
	 * 
	 * A MainGame is where the game loop runs, it will contain/control elements that rely on the game clock(update method, draw method for animation)
	 * 
	 */
	public MainGame() {
		setupVariables();
		this.setSize(800,800);
		this.add(mapView);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		runGameLoop();
	}

	/**
	 * @author Cody Jensen
	 * 
	 * eventually time reliant display windows will go here
	 */
	private void setupVariables() {
		mapView = new mapView();
		map = new Map();
	}

	/**
	 * @author Cody Jensen
	 * 
	 * This method calls gameLoop in a new thread
	 */
	public void runGameLoop()
	{
		Thread loop = new Thread()
		{
			public void run()
			{
				gameLoop();
			}
		};
		loop.start();
	}

	/**
	 * @author Cody Jensen
	 * 
	 * this is where the actual game timer is run.  Inside loopRunnable's run() method --call methods that rely on the game loop
	 */
	private void gameLoop()
	{
		timer = new Timer();
		timer.schedule(new loopRunnable(), 0, 3000); //new timer at 60 fps, the timing mechanism
	}

	private class loopRunnable extends java.util.TimerTask
	{
		public void run() //this becomes the loop
		{
			updateGame();
			System.out.println("UPDATE");
			//draw the game here
			//render();

			if (!running)
			{
				timer.cancel();
			}
		}
	}

	private void updateGame()
	{
		for(int i = 0; i < droneList.size(); i++){
			droneList.get(i).executeTaskList();
		}
		mapView.setTextArea(map.toString());
	}

	private void drawGame(float interpolation)
	{
		//gamePanel.repaint();
	}

}

