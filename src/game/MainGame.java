package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;





import buildings.Base;
import buildings.Building;
import buildings.SolarPlant;
import model.Battery;
import model.Drone;
import model.WeatherBehavior;
import resources.Hydrogen;
import tiles.Tile;
import view.GraphicView;
import view.TextView;
import model.*;
import resources.*;
import task.*;
import tiles.*;
import view.*;

public class MainGame extends JFrame{

	private TextView textView;
	private static Map map;
	private GraphicView graphics;

	private JTabbedPane panes;

	private static LinkedList<Tile> resourceList = new LinkedList<Tile>();
	private static LinkedList<Building> buildingList = new LinkedList<Building>();

	private boolean running = true;
	private boolean paused = false;
	private int fps = 60;
	private int frameCount = 0;
	private Timer timer;

	private static ArrayList<ArrayList<Drone>> allDrones = new ArrayList<ArrayList<Drone>>();
	private static ArrayList<Drone> defaultList = new ArrayList<Drone>();
	private static ArrayList<Drone> builders = new ArrayList<Drone>();
	private static ArrayList<Drone> miners = new ArrayList<Drone>();
	private static ArrayList<Drone> resourceCollectors = new ArrayList<Drone>();
	private static ArrayList<Drone> itemBuilders = new ArrayList<Drone>();
	

	private static Drone startDroneOne, startDroneTwo, startDroneThree, startDroneFour, startDroneFive;
	private static Building base, plant1;

	public static void main(String[] args)
	{
		MainGame game = new MainGame();
		initializeDrones();	
		initializeBuildings();
		game.setVisible(true);

	}
	private static void initializeBuildings() {
		base = new Base(10, 10);
		
		//when a game is started if main base cannot be built generate new map
		if(base.canBuild(map.getTile(10,10)) != true){
			map = new Map(6);
			initializeBuildings();
		}
		
		map.build(base);
		base.setFinished();
		buildingList.add(base);
	}
	/**
	 * @author Cody Jensen
	 * 
	 * This is where the starting groups of drones will be added, later a method to add a drone to the map can be added to replace this
	 */
	private static void initializeDrones() {

		
		startDroneOne = new Drone("startDroneOne", 400.0, map.getTile(10,15));	
		startDroneTwo = new Drone("startDroneTwo", 400.0, map.getTile(15,15));
		startDroneThree = new Drone("startDroneThree", 400.0, map.getTile(17,17));
		startDroneFour = new Drone("startDroneFour", 400.0, map.getTile(20,21));
		startDroneFive = new Drone("startDroneFive", 400.0, map.getTile(20, 15));
		
		defaultList.add(startDroneOne);
		defaultList.add(startDroneTwo);
		defaultList.add(startDroneThree);
		defaultList.add(startDroneFour);
		defaultList.add(startDroneFive);

		allDrones.add(defaultList);
		allDrones.add(miners);
		allDrones.add(builders);
		allDrones.add(resourceCollectors);
		allDrones.add(itemBuilders);
		
	}

	/**
	 * @author Cody Jensen
	 * 
	 * A MainGame is where the game loop runs, it will contain/control elements that rely on the game clock(update method, draw method for animation)
	 * 
	 */
	public MainGame() {
		setupVariables();
		this.setSize(1000,1000);
		this.add(panes);
		textView.setLocation(0, 0);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		runGameLoop();
	}

	/**
	 * @author Cody Jensen
	 * 
	 * eventually time reliant display windows will go here
	 */
	private void setupVariables() {
		int n = JOptionPane.showConfirmDialog(null, "Do you want to enter a seed?", "Do you want to enter a seed?", JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.NO_OPTION) {
			map = new Map(6);
		} else {
			String s = JOptionPane.showInputDialog("Enter a long:");
			map = new Map(6, Long.parseLong(s));
		}
		
		textView = new TextView(map, 5, 5, 20, 20);
		graphics = new GraphicView(map, 5 ,5, 20, 20, textView);
		graphics.setLocation(0, 0);
		graphics.setFocusable(true);
		textView.setFocusable(true);
	
		
		
		panes = new JTabbedPane();
		panes.add("Text View", textView);
		panes.add("Graphic View", graphics);
		panes.setFocusable(false);
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
		timer.schedule(new loopRunnable(), 0, 1000); //new timer at 60 fps, the timing mechanism
	}

	private class loopRunnable extends java.util.TimerTask
	{
		int x = 0;
		public void run() //this becomes the loop
		{
			//assignTasks();
			if (x == 5) {
				buildTasks();
			}
			if (x >= 15) {
				itemBuildTasks();
			}
			if (x>= 25) {
				resourceTasks();
			}
			WeatherBehavior.LightMovement(map);
			doDroneTasks();
			System.out.println("Current Game Loop Update: " + x);
			drawGame();
			x++;
			if (!running)
			{
				timer.cancel();
			}
		}
	}

	
	private void assignTasks(){
		buildTasks();
		mineTasks();
		itemBuildTasks();
		resourceTasks();
	}
	
	private void resourceTasks() {
		for (int i = 0; i < resourceCollectors.size(); i++) {
			resourceCollectors.get(i).getTaskList().push(new ResourceTask(resourceCollectors.get(i), buildingList.get(0)));
		}
	}
	
	private void buildTasks() {
		
		for (int i = 0; i < buildingList.size(); i++) {
			for (int j = 0; j < builders.size(); j++) {
				if(!buildingList.get(i).isFinished()){
						builders.get(j).getTaskList().push(new BuildTask(builders.get(j), buildingList.get(i)));
						System.out.println("Builder has been assigned a building task.");
				}
			}
		}
	}
	
	private void mineTasks() {
		//
		
	}
	
	private void itemBuildTasks() {
		try {
			Drone drone = allDrones.get(0).get(0);
			if (drone.getPower() / drone.getMaxPower() < .5 && !drone.hasItem()) {
				System.out.println("Time to make a battery");
				drone.getTaskList().push(new ItemBuildTask(drone, new Battery()));
			}
		} catch (Exception e) {}
	}
	
	private void doDroneTasks() {
		// Goes through every drone, checks if they're dead and removes them if they are. 
		// Then it calls execute on every drone's current task.
		System.out.println("**************************************************************");
		for(int i = 0; i < allDrones.size(); i++){
			for(int j = 0; j< allDrones.get(i).size(); j++){
				allDrones.get(i).get(j).executeTaskList(map);
			}
		}
		System.out.println("**************************************************************");
	}
	private void drawGame(){
		graphics.repaint();
		textView.repaint();
	}


}

