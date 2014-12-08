package view;

import game.MainGame;
import game.TestBuildingGame;

import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.WeatherBehavior;

public class TestMainGUI extends JFrame{

	private static GraphicView graphics;
	private TextView textView;
	
	private TestBuildingGame mainGame;
	private Timer timer;
	
	private boolean running = true;
	private boolean paused = false;
	private int frameCount = 0;
	private boolean win = false;
	
	public static void main(String[] args){
		new TestMainGUI();
		graphics.setFocusable(true);
	}
	
	public TestMainGUI(){
		
		mainGame = new TestBuildingGame();
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
		
		
		this.add(graphics);
		
	}

	public void drawGame(){
		graphics.repaint();
		textView.repaint();
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
		timer.schedule(new loopRunnable(), 0, 4000 / 2); 
	}

	private class loopRunnable extends java.util.TimerTask
	{
		int x = 0;
		public void run() //this becomes the loop
		{
			mainGame.assignTasks();
			mainGame.doWeather();
			mainGame.doBuildingTasks();
			mainGame.doDroneTasksTest();
			mainGame.printTest();
			win = mainGame.checkWin();
			System.out.println("Current Game Loop Update: " + x);
			drawGame();
			x++;

			
			//for debug purposes!!!
			if(x == 20){
				mainGame.debugMethod2();
			}
			if (!running) //Comment out this if-statement to show the win condition triggering.
				//Getting the master up-to-date was a little weird, since both Cody and I pushed stuff separately

			if (!running || win)

			{
				timer.cancel();
				System.out.println("You won.");
			}
		}
	}
	
}
