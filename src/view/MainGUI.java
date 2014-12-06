package view;

import game.MainGame;

import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.WeatherBehavior;

public class MainGUI extends JFrame{

	private static GraphicView graphics;
	private TextView textView;
	private floatingPanel floating;
	
	private MainGame mainGame;
	private Timer timer;
	
	private boolean running = true;
	private boolean paused = false;
	private int frameCount = 0;
	
	public static void main(String[] args){
		new MainGUI();
		graphics.setFocusable(true);
	}
	
	public MainGUI(){
		
		mainGame = new MainGame();
		floating = new floatingPanel();
		
		this.add(floating);
		setupMapPane();
		this.setVisible(true);
		this.setSize(1020,1020);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		runGameLoop();
		
		
	}
	
	private void setupMapPane() {
		
		textView = new TextView(mainGame.getMap(), 5, 5, 20, 20);
		graphics = new GraphicView(mainGame.getMap(), 5 ,5, 20, 20, textView);
		graphics.setLocation(0, 0);
		
		graphics.setFocusable(true);
		textView.setFocusable(true);
		
		System.out.println(graphics.isFocusOwner());
		
		this.add(graphics);
		
	}

	public void drawGame(){
		graphics.repaint();
		textView.repaint();
		floating.repaint();
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
		timer.schedule(new loopRunnable(), 0, 1000 / 2); 
	}

	private class loopRunnable extends java.util.TimerTask
	{
		int x = 0;
		public void run() //this becomes the loop
		{
//			mainGame.assignTasks();
//			mainGame.doWeather();
//			mainGame.doBuildingTasks();
			mainGame.doDroneTasksTest();
			System.out.println("Current Game Loop Update: " + x);
			drawGame();
			x++;
			
			//for debug purposes!!!
			if(x == 20){
				mainGame.debugMethod2();
			}
			if (!running)
			{
				timer.cancel();
			}
		}
	}
	
}
