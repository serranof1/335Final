package view;

import game.MainGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import buildings.Base;
import model.WeatherBehavior;

/**
 * MainGUI is a JFrame that holds the full game, graphical and model.
 * @author Team Rosetta
 *
 */
public class MainGUI extends JFrame{

	private static GraphicView graphics;
	private TextView textView;
	
	private MainGame mainGame;
	private Timer timer;
	private Base base;
	
	private boolean running = true;
	private boolean paused = false;
	private int frameCount = 0;
	private boolean win = false;
	private boolean lose = false;
	
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemLoad;
	
	public static void main(String[] args){
		new MainGUI();
		graphics.setFocusable(true);
	}
	
	public MainGUI(){
		
		mainGame = new MainGame();
		base = (Base) mainGame.getBuildingList().get(0);
		setupMapPane();
		setupMenuBar();
		this.setVisible(true);
		this.setSize(1200,900);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new SaveDataListener());
		runGameLoop();
		
		
	}
	
	private void setupMapPane() {
		
		graphics = new GraphicView(mainGame, 5 ,5, 50, 50);
		graphics.setLocation(0, 0);
		
		graphics.setFocusable(true);
		
		System.out.println(graphics.isFocusOwner());
		
		this.add(graphics);
		
	}

	public void drawGame(){
		graphics.repaint();
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
		timer.schedule(new loopRunnable(), 0, 2000 / 2); 
	}

	private class loopRunnable extends java.util.TimerTask
	{
		int x = 0;
		public void run() //this becomes the loop
		{
//			mainGame.assignTasks();
			mainGame.doWeather();
			mainGame.doBuildingTasks();
			mainGame.assignAndDoTasks();
			win = mainGame.checkWin();
			lose = mainGame.checkLose();
			System.out.println("Current Game Loop Update: " + x);
			drawGame();
			x++;

			if (!running) //Comment out this if-statement to show the win condition triggering.
				//Getting the master up-to-date was a little weird, since both Cody and I pushed stuff separately

			if (!running || win || lose)

			{
				timer.cancel();
				if (win); System.out.println("You won.");
				if (lose); System.out.println("You lose.");
			}
		}
	}
	
	private void setupMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		
		menuBar.add(menu);
		
		menuItemSave = new JMenuItem("Save Game");
		menuItemLoad = new JMenuItem("Load Game");
		menuItemSave.addActionListener(new GameDataListener());
		menuItemLoad.addActionListener(new GameDataListener());
		
		menu.add(menuItemSave);
		menu.add(menuItemLoad);
		this.setJMenuBar(menuBar);
	}
	
	private class GameDataListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			timer.cancel();
			if(e.getActionCommand().compareTo("Save Game") == 0)
				mainGame.saveGame();
			else
				mainGame.loadGame();
		}
	}
	
	private class SaveDataListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent e) {
			timer.cancel();
			mainGame.saveGame();
		}

		@Override
		public void windowClosed(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowDeactivated(WindowEvent e) {}
		
	}
}
