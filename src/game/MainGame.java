package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import model.Base;
import model.BuildTask;
import model.Building;
import model.DeadTask;
import model.Drone;
import model.SolarPlant;
import resources.Hydrogen;
import tiles.Tile;
import view.GraphicView;
import view.TextView;


public class MainGame extends JFrame{


	private TextView textView;
	private static Map map;
	private GraphicView graphics;

	private JTabbedPane panes;

	private static LinkedList<Drone> droneList = new LinkedList<Drone>();
	private static LinkedList<Tile> resourceList = new LinkedList<Tile>();
	private static LinkedList<Building> buildingList = new LinkedList<Building>();

	private boolean running = true;
	private boolean paused = false;
	private int fps = 60;
	private int frameCount = 0;
	private Timer timer;


	private static Drone drone1, drone2, drone3, drone4, drone5, drone6;

	public static void main(String[] args)
	{
		MainGame game = new MainGame();
		initializeBuildings();
		initializeDrones();	
		game.setVisible(true);

	}
	private static void initializeBuildings() {
		// TODO Auto-generated method stub
		Base base = new Base(10, 10, map.getTile(10,10));
		map.build(base);
		
		//Constructed with methane as its resource, should not be in final.
		//Resource may not even be necessary in the constructor.
		SolarPlant plant1 = new SolarPlant(10, 15, new Hydrogen(), map.getTile(10, 15));
		map.build(plant1);
		
		buildingList.add(plant1);
		//buildingList.add(plant1);
	}
	/**
	 * @author Cody Jensen
	 * 
	 * This is where the starting groups of drones will be added, later a method to add a drone to the map can be added to replace this
	 */
	private static void initializeDrones() {

		drone1 = new Drone(100.0, map.getTile(10,15));
		//drone1.getTaskList().push(new BuildTask(drone1, plant1);
		
		//drone2 = new Drone(100.0, map.getTile(30,30)); //sets a drone out to die
//		drone3 = new Drone(100.0, map.getTile(8,17));
//		drone4 = new Drone(100.0, map.getTile(6,12));
//		drone5 = new Drone(100.0, map.getTile(5, 10));
//		drone6 = new Drone(100.0, map.getTile(8,9));

		droneList.add(drone1);
		//droneList.add(drone2);
//		droneList.add(drone3);
//		droneList.add(drone4);
//		droneList.add(drone5);
//		droneList.add(drone6);
		


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
			map = new Map(10);
		} else {
			String s = JOptionPane.showInputDialog("Enter a long:");
			map = new Map(10, Long.parseLong(s));
		}
		
		textView = new TextView(map, 5, 5, 20, 20);
		graphics = new GraphicView(map, 5 ,5, 20, 20);
		graphics.setLocation(0, 0);
		graphics.setFocusable(true);
		textView.setFocusable(true);
	
		
		graphics.addKeyListener(new KeyMoveListener());
		textView.addKeyListener(new KeyMoveListener());
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
		timer.schedule(new loopRunnable(), 0, 3000); //new timer at 60 fps, the timing mechanism
	}

	private class loopRunnable extends java.util.TimerTask
	{
		int x = 0;
		public void run() //this becomes the loop
		{
			updateGame();
			System.out.println("Current Game Loop Update: " + x);
			drawGame();
			x++;
			if (!running)
			{
				timer.cancel();
			}
		}
	}

	private void updateGame()
	{
		for(int i = 0; i < droneList.size(); i++){
			droneList.get(i).executeTaskList(map);
			if(droneList.get(i).getPower()== 0){
				//droneList.remove(droneList.get(i));
			}
			if(droneList.size()== 0){
				System.out.println("You have no drones left. You lose.");
			}
		}
		for(int i = 0; i<buildingList.size(); i++){
			if(buildingList.get(i).isFinishBuilt()){
				drone1.getTaskList().push(new BuildTask(drone1, buildingList.get(i)));
				System.out.println("Pushed a new build task onto drone");
			}
			buildingList.get(i).executeOnBuilding(map);
		}
		
	}

	private void drawGame(){
		graphics.repaint();
		textView.repaint();
	}

	private class KeyMoveListener implements KeyListener
	{
		public void keyPressed(KeyEvent arg0) {
			switch(arg0.getKeyCode())
			{
			case KeyEvent.VK_UP: 
				if((graphics.getLeftRow() > 0) || (textView.getLeftRow() > 0)){
					graphics.setLeftRow(-1);
					System.out.println("UP :" +graphics.getLeftRow());
					textView.setLeftRow(-1);
					textView.repaint();
					graphics.repaint();
				} 
				break;

			case KeyEvent.VK_DOWN: 
				if((graphics.getLeftRow() < map.getSize() - graphics.getViewHeight()) || (textView.getLeftRow() < map.getSize() - graphics.getViewHeight())){
					graphics.setLeftRow(1);
					System.out.println("DOWN :" +(map.getSize() - graphics.getViewHeight()));
					textView.setLeftRow(1);
					textView.repaint();
					graphics.repaint();
				}
				break;

			case KeyEvent.VK_LEFT: 
				if((graphics.getLeftCol() > 0) || (textView.getLeftCol() > 0)){
					graphics.setLeftCol(-1);
					System.out.println("LEFT :" +graphics.getLeftCol());
					textView.setLeftCol(-1);
					textView.repaint();
					graphics.repaint();
				}
				break;

			case KeyEvent.VK_RIGHT: 
				if((graphics.getLeftCol() < (map.getSize() - graphics.getViewLength())) || (textView.getLeftCol() < (map.getSize() - graphics.getViewLength()))){
					graphics.setLeftCol(1);
					System.out.println("RIGHT :" +graphics.getLeftCol());
					textView.setLeftCol(1);
					textView.repaint();
					graphics.repaint();
				}
				break;
			default:
				break;
			}

		}
		//this is a comment
		public void keyReleased(KeyEvent arg0) {}
		public void keyTyped(KeyEvent arg0) {}
	}
}

