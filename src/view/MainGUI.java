package view;

import game.MainGame;

import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.WeatherBehavior;

public class MainGUI extends JFrame{

	private static GraphicView graphics;
	private TextView textView;
	
	private JTabbedPane panes;
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
		setupMapPane();
		this.setVisible(true);
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		runGameLoop();
		
		
	}
	
	private void setupMapPane() {
		panes = new JTabbedPane();
		panes.setFocusable(false);
		
		textView = new TextView(mainGame.getMap(), 5, 5, 20, 20);
		graphics = new GraphicView(mainGame.getMap(), 5 ,5, 20, 20, textView);
		graphics.setLocation(0, 0);
		
		graphics.setFocusable(true);
		textView.setFocusable(true);
		
		System.out.println(graphics.isFocusOwner());
		panes.add("Text View", textView);
		panes.add("Graphic View", graphics);
	
		this.add(panes);
		
	}

	public void drawGame(){
		System.out.println(graphics);
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
		timer.schedule(new loopRunnable(), 0, 3000); 
	}

	private class loopRunnable extends java.util.TimerTask
	{
		int x = 0;
		public void run() //this becomes the loop
		{
			mainGame.doWeather();
			mainGame.doDroneTasks();
			System.out.println("Current Game Loop Update: " + x);
			drawGame();
			x++;
			if (!running)
			{
				timer.cancel();
			}
		}
	}
	
}
