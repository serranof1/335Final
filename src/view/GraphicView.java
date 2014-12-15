package view;

import game.MainGame;
import game.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ListOfLists;
import tiles.Tile;
import buildings.Base;
import buildings.Building;
import buildings.Engineering;
import buildings.Farm;
import buildings.MethanePlant;
import buildings.SolarPlant;

/**
 * GraphicView is a JPanel for the graphical representation of our game.
 * @author Team Rosetta
 *
 */
public class GraphicView extends JPanel{

	private Map map;
	private int leftRow, leftCol;
	private int viewLength, viewHeight;
	private int clicks1;
	private int startPointX, startPointY, endPointY, endPointX;
	private int clicks = 0;
	private int currentSelection;
	
	//each button buildMine and after needs to be implemented
	private JButton build, collect, makeDrone, buildSolarPlant, buildEngineering, buildMethanePlant, buildFarm, buildBattery, buildRepairBox, buildGasTank, buildTreads, buildJetpack, buildBoat;
	private JButton downArrow1, downArrow2, downArrow3, downArrow4;
	private JButton upArrow1, upArrow2, upArrow3, upArrow4;
	private JPanel userInfo;
	private JTextField defNum, buildNum, mineNum, collectNum, itemNum;
	private StockpilePanel stockPileInfo;

	private boolean selectResource;
	public boolean dragging;
	
	private MainGame mainGame;
	private ListOfLists allDrones;

	private Tile activeTile;
	
	public GraphicView(MainGame mainGame, int row, int col , int viewHeight, int viewLength) {
		currentSelection = 0;
		clicks1 = 0;
		this.mainGame = mainGame;
		allDrones = mainGame.getAllDrones();
		this.setBackground(Color.BLACK);
		this.viewHeight = viewHeight;
		this.viewLength = viewLength;
		this.setLayout(new BorderLayout());
		userInfo = new JPanel();
		userInfo.setBackground(Color.WHITE);
		stockPileInfo = new StockpilePanel();
		stockPileInfo.setSize(stockPileInfo.getSize());
		
		userInfo.setLayout(new GridBagLayout());
		this.add(userInfo, BorderLayout.EAST);
		this.add(stockPileInfo, BorderLayout.SOUTH);
		
		GridBagConstraints c = new GridBagConstraints();
		
		makeDrone = new JButton("Drone");
		c.anchor = GridBagConstraints.NORTH;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		userInfo.add(makeDrone, c);

		build = new JButton("Build");
		c.gridx = 1;
		c.gridwidth = 2;
		userInfo.add(build, c);

		collect = new JButton("Collect");
		c.ipady =20;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		userInfo.add(collect, c);
		
		
		JLabel list0 = new JLabel("Default: ");
		c.ipady =15;
		c.weightx = 0.5;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 3;
		userInfo.add(list0, c);
		
		defNum = new JTextField("" + mainGame.getAllDrones().get(0).size());
		defNum.setEditable(false);
		c.gridx = 2;
		userInfo.add(defNum, c);
		
		
		JLabel list1 = new JLabel("Builders: ");
		c.gridx = 0;
		c.gridy = 4;
		userInfo.add(list1, c);
		
		buildNum = new JTextField("" + mainGame.getAllDrones().get(1).size());
		buildNum.setEditable(false);
		c.gridx = 1;
		userInfo.add(buildNum, c);
		
		c.gridx = 2;
		upArrow1 = new JButton("+");
		userInfo.add(upArrow1, c);
		
		JLabel list3 = new JLabel("Collectors: ");
		c.gridx = 0;
		c.gridy = 6;
		userInfo.add(list3, c);
		
		collectNum = new JTextField("" + mainGame.getAllDrones().get(2).size());
		collectNum.setEditable(false);
		c.gridx = 1;
		userInfo.add(collectNum, c);

		c.gridx = 2;
		upArrow3 = new JButton("+");
		userInfo.add(upArrow3, c);
		
		
		JLabel list4 = new JLabel("Item Builders: ");
		c.gridx = 0;
		c.gridy = 7;
		userInfo.add(list4, c);
		
		itemNum = new JTextField("" + mainGame.getAllDrones().get(3).size());
		itemNum.setEditable(false);
		c.gridx = 1;
		userInfo.add(itemNum, c);

		c.gridx = 2;
		upArrow4 = new JButton("+");
		userInfo.add(upArrow4, c);
		
		buildSolarPlant = new JButton("Solar Plant");
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 3;
		userInfo.add(buildSolarPlant, c);
		
		buildEngineering = new JButton("Engineering");
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 3;
		userInfo.add(buildEngineering, c);
		
		buildMethanePlant = new JButton("Methane Plant");
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 3;
		userInfo.add(buildMethanePlant, c);
		
		buildFarm = new JButton("Farm");
		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 3;
		userInfo.add(buildFarm, c);
		
		c.gridwidth = 1;
		buildBattery = new JButton("Battery");
		c.gridx = 0;
		c.gridy = 12;
		userInfo.add(buildBattery, c);
		
		buildRepairBox = new JButton("Repair Box");
		c.gridx = 2;
		c.gridy = 12;
		userInfo.add(buildRepairBox, c);
		
		buildGasTank = new JButton("Gas Tank");
		c.gridx = 0;
		c.gridy = 13;
		userInfo.add(buildGasTank, c);
		
		buildTreads = new JButton("Treads");
		c.gridx = 2;
		c.gridy = 13;
		userInfo.add(buildTreads, c);
		
		buildJetpack = new JButton("Jetpack");
		c.gridx = 0;
		c.gridy = 14;
		userInfo.add(buildJetpack, c);
		
		buildBoat = new JButton("Boat");
		c.gridx = 2;
		c.gridy = 14;
		userInfo.add(buildBoat, c);
		
		JLabel fakeLabel = new JLabel("");
		c.ipady = 0;
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.EAST; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 2;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 15;       //third row
		userInfo.add(fakeLabel, c);

		buildSolarPlant.setEnabled(false);
		buildEngineering.setEnabled(false);
		buildMethanePlant.setEnabled(false);
		buildFarm.setEnabled(false);
		
		leftRow = row;
		leftCol = col;
		selectResource = false;
		this.map = mainGame.getMap();
		registerListeners();
	}
	

	public void setLeftCol(int input){
		leftCol += input;
	}

	public void setLeftRow(int input){
		leftRow += input;
	}

	public int getLeftCol(){
		return leftCol;
	}

	public int getLeftRow(){
		return leftRow;
	}

	private void drawMap() {

		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		int row = leftRow;
		int col = leftCol;

		for (int i = 0; i < viewHeight; i++){
			for (int j = viewLength; j > 0; j--) { // Changed loop condition here.
				stockPileInfo.drawResources(this.getResourceString());
				defNum.setText(""+mainGame.getAllDrones().get(0).size());
				buildNum.setText(""+mainGame.getAllDrones().get(1).size());
				
				collectNum.setText(""+mainGame.getAllDrones().get(2).size());
				itemNum.setText(""+mainGame.getAllDrones().get(3).size());
				
				map.getTile(col, row).draw(g, (viewLength - j) * 25, i * 25);
				col++;
			}
			col = leftCol;
			row++;
		}

		if(selectResource && clicks == 1){
			int topLeftX = startPointX / 25;
			int topLeftY = startPointY / 25;
			int bottomRightX = endPointX / 25;
			int bottomRightY = endPointY / 25;
			Color myGreen = new Color(0 , 255 , 0, 125);


			if (topLeftX > bottomRightX && topLeftY > bottomRightY) {
				g.setColor(myGreen);	
				g.fillRect( bottomRightX * 25, bottomRightY * 25, (topLeftX - bottomRightX) * 25, (topLeftY - bottomRightY) * 25);

				g.setColor(Color.GREEN);
				g.drawRect( bottomRightX * 25, bottomRightY * 25, (topLeftX - bottomRightX) * 25, (topLeftY - bottomRightY) * 25);

			} else if (topLeftX < bottomRightX && topLeftY > bottomRightY) {
				g.setColor(myGreen);
				g.fillRect(topLeftX * 25, bottomRightY * 25, (bottomRightX - topLeftX) * 25, (topLeftY - bottomRightY) * 25);

				g.setColor(Color.GREEN);
				g.drawRect(topLeftX * 25, bottomRightY * 25, (bottomRightX - topLeftX) * 25, (topLeftY - bottomRightY) * 25);
			} else if (topLeftX > bottomRightX && topLeftY < bottomRightY) {
				g.setColor(myGreen);	
				g.fillRect(bottomRightX * 25, topLeftY * 25, (topLeftX -bottomRightX) * 25, (bottomRightY - topLeftY) * 25);

				g.setColor(Color.GREEN);
				g.drawRect(bottomRightX * 25, topLeftY * 25, (topLeftX -bottomRightX) * 25, (bottomRightY - topLeftY) * 25);
			} else {
				g.setColor(myGreen);	
				g.fillRect(topLeftX * 25, topLeftY * 25 , (bottomRightX - topLeftX) * 25, (bottomRightY - topLeftY) * 25);

				g.setColor(Color.GREEN);
				g.drawRect(topLeftX * 25, topLeftY * 25 , (bottomRightX - topLeftX) * 25, (bottomRightY - topLeftY) * 25);
			}

		}

	}

	private String getResourceString() {
		String resource = ((Base) mainGame.getBuildingList().get(0)).getStockPile();

		return resource;
	}


	public int getViewLength() {
		return viewLength;
	}

	public void setViewLength(int viewLength) {
		this.viewLength = viewLength;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public void setViewHeight(int viewHeight) {
		this.viewHeight = viewHeight;
	}

	private void registerListeners() {

		MouseListener listener = new ListenToMouse();
		MouseMotionListener motionListener = new ListenToMouse();
		CollectListener collectListener= new CollectListener();
	
		this.addMouseMotionListener(motionListener);
		this.addMouseListener(listener);
		collect.addActionListener(new CollectListener());
		makeDrone.addActionListener(new MakeDroneListener());
		upArrow1.addActionListener(new AddToBuildersListener());
		upArrow3.addActionListener(new AddToCollectorsListener());
		upArrow4.addActionListener(new AddToItemBuildersListener());
		this.addKeyListener(new KeyMoveListener());
		
		ActionListener buttonListen = new ButtonListener();
		build.addActionListener(buttonListen);
		collect.addActionListener(buttonListen);
		buildSolarPlant.addActionListener(buttonListen);
		buildEngineering.addActionListener(buttonListen);
		buildMethanePlant.addActionListener(buttonListen);
		buildFarm.addActionListener(buttonListen);
		
	}
	
	private class AddToBuildersListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Button Pressed");
			if(allDrones.get(0).size()>0)
				allDrones.moveFromDefaultList(allDrones.get(0).get(0), allDrones.get(1));
			grabFocus();
		}
	}
	
	private class AddToCollectorsListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Button Pressed");
			if(allDrones.get(0).size()>0)
				allDrones.moveFromDefaultList(allDrones.get(0).get(0), allDrones.get(2));
			grabFocus();
		}
	}
	
	private class AddToItemBuildersListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Button Pressed");
			if(allDrones.get(0).size()>0)
				allDrones.moveFromDefaultList(allDrones.get(0).get(0), allDrones.get(3));
			grabFocus();
		}
	}

	private class CollectListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				setSelectResource(true);
				grabFocus();
			}
	}
	private class MakeDroneListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				mainGame.createDrone();
				grabFocus();
			}
	}
	/**
	 * This is where we tell the mouse what to do on clicks and movement
	 * @author Cody Jensen
	 *
	 */
	private class ListenToMouse implements MouseMotionListener, MouseListener {

		public void mouseClicked2(MouseEvent evt) {
			
			// for information panel
			if(!selectResource){
				System.out.println("x:  " +((evt.getX() / 25) + leftCol) +"   y:  " +(((evt.getY() / 25) + leftRow)));
				activeTile = map.getTile(((evt.getX() / 25) + leftCol), ((evt.getY() / 25) + leftRow));
				
				if(activeTile.getHasDrone()){
					stockPileInfo.drawUpdateField(mainGame.getAllDrones().getDroneInformation(activeTile.getX(), activeTile.getY()));
				} else {
				stockPileInfo.drawUpdateField(activeTile.toString());
				}
			}
			
			
			// for resource collection
			Point upperLeft = new Point();
			Point bottomRight = new Point();
			
			if(selectResource && clicks == 0){
				startPointX = evt.getX();
				startPointY = evt.getY();
				clicks++;
				System.out.println("mouse click 1:  " +clicks);

			} else if(selectResource && clicks == 1){
				System.out.println("mouse click 2:   " +clicks);
				clicks--;
				selectResource = false;
				//add resources to list here
				System.out.println("startX  " +startPointX);
				upperLeft.x = startPointX;
				upperLeft.y = startPointY;
				bottomRight.x = endPointX;
				bottomRight.y = endPointY;
				
				globalizePoints(upperLeft, bottomRight);
				mainGame.gatherResources(upperLeft, bottomRight);
				grabFocus();
			} else {
				//do other things here, im not sure what
			}
		}
		
		public void mouseClicked(MouseEvent evt) {
			
			if(currentSelection == 0){
				System.out.println("x:  " +((evt.getX() / 25) + leftCol) +"   y:  " +(((evt.getY() / 25) + leftRow)));
				activeTile = map.getTile(((evt.getX() / 25) + leftCol), ((evt.getY() / 25) + leftRow));
				
				if(activeTile.getHasDrone()){
					stockPileInfo.drawUpdateField(mainGame.getAllDrones().getDroneInformation(activeTile.getX(), activeTile.getY()));
				} else {
				stockPileInfo.drawUpdateField(activeTile.toString());
				}
			}
			System.out.println("mouse clicked");
			System.out.println(currentSelection);
			
			// for resource collection
			Point upperLeft = new Point();
			Point bottomRight = new Point();
			
			if(currentSelection == 11 && clicks == 0){
				startPointX = evt.getX();
				startPointY = evt.getY();
				clicks++;
				System.out.println("mouse click 1:  " +clicks);

			} else if(currentSelection == 11 && clicks == 1){
				System.out.println("mouse click 2:   " +clicks);
				clicks--;
				currentSelection = 0;
				//add resources to list here
				System.out.println("startX  " +startPointX);
				upperLeft.x = startPointX;
				upperLeft.y = startPointY;
				bottomRight.x = endPointX;
				bottomRight.y = endPointY;
				
				globalizePoints(upperLeft, bottomRight);
				mainGame.gatherResources(upperLeft, bottomRight);
				grabFocus();
			} 
			if(currentSelection == 1){
				startPointX = evt.getX();
				startPointY = evt.getY();
				upperLeft.x = startPointX;
				upperLeft.y = startPointY;
				bottomRight.x = startPointX;
				bottomRight.y = startPointY;
				
				globalizePoints(upperLeft, bottomRight);
				System.out.println(upperLeft.x + " " + upperLeft.y);
				Building toBeBuilt = new SolarPlant(upperLeft.y, upperLeft.x);
				//mainGame.placeBuilding(toBeBuilt);
				mainGame.addToBuildingList(toBeBuilt);
				grabFocus();
				currentSelection = 0;
			}
			if(currentSelection == 2){
				startPointX = evt.getX();
				startPointY = evt.getY();
				startPointX = evt.getX();
				startPointY = evt.getY();
				upperLeft.x = startPointX;
				upperLeft.y = startPointY;
				bottomRight.x = startPointX;
				bottomRight.y = startPointY;
				globalizePoints(upperLeft, bottomRight);
				Building toBeBuilt = new Engineering(upperLeft.y, upperLeft.x);
				//mainGame.placeBuilding(toBeBuilt);
				mainGame.addToBuildingList(toBeBuilt);
				grabFocus();
				currentSelection = 0;
			}
			if(currentSelection == 3){
				startPointX = evt.getX();
				startPointY = evt.getY();
				startPointX = evt.getX();
				startPointY = evt.getY();
				upperLeft.x = startPointX;
				upperLeft.y = startPointY;
				bottomRight.x = startPointX;
				bottomRight.y = startPointY;
				globalizePoints(upperLeft, bottomRight);
				Building toBeBuilt = new MethanePlant(upperLeft.y, upperLeft.x);
				//mainGame.placeBuilding(toBeBuilt);
				mainGame.addToBuildingList(toBeBuilt);
				grabFocus();
				currentSelection = 0;
			}
			if(currentSelection == 4){
				startPointX = evt.getX();
				startPointY = evt.getY();
				startPointX = evt.getX();
				startPointY = evt.getY();
				upperLeft.x = startPointX;
				upperLeft.y = startPointY;
				bottomRight.x = startPointX;
				bottomRight.y = startPointY;
				globalizePoints(upperLeft, bottomRight);
				Building toBeBuilt = new Farm(upperLeft.y, upperLeft.x);
				//mainGame.placeBuilding(toBeBuilt);
				mainGame.addToBuildingList(toBeBuilt);
				grabFocus();
				currentSelection = 0;
			}
			buildSolarPlant.setEnabled(false);
			buildEngineering.setEnabled(false);
			buildMethanePlant.setEnabled(false);
			buildFarm.setEnabled(false);
		}

		private void globalizePoints(Point upperLeft, Point bottomRight) {
			
			upperLeft.x = leftCol + (upperLeft.x / 25);
			upperLeft.y = leftRow + (upperLeft.y / 25);
			
			bottomRight.x = leftCol + (bottomRight.x / 25);
			bottomRight.y = leftRow + (bottomRight.y / 25);
			
			Point newUpperLeft = new Point();
			Point newBottomRight = new Point();
			
			newUpperLeft.x = Math.min(upperLeft.x, bottomRight.x);
			newUpperLeft.y = Math.min(upperLeft.y, bottomRight.y);
			
			newBottomRight.x = Math.max(upperLeft.x, bottomRight.x);
			newBottomRight.y = Math.max(upperLeft.y, bottomRight.y);
			
			upperLeft = newUpperLeft;
			bottomRight = newBottomRight;
			
			
		}

		public void mouseMoved(MouseEvent evt) {
			endPointX = evt.getX();
			endPointY = evt.getY();
			if(currentSelection > 0 && clicks == 1)
				repaint();
		}

		public void mousePressed(MouseEvent evt) {
		}

		public void mouseEntered(MouseEvent evt) {
		}

		public void mouseReleased(MouseEvent evt) {
		}

		public void mouseExited(MouseEvent evt) {
		}

		public void mouseDragged(MouseEvent evt) {

		}
	}
	
	private class KeyMoveListener implements KeyListener
	{
		public void keyPressed(KeyEvent arg0) {
			switch(arg0.getKeyCode())
			{
			case KeyEvent.VK_UP: 
				if(leftRow > 0){
					leftRow--;
					repaint();
				} 
				break;

			case KeyEvent.VK_DOWN: 
				if(leftRow < map.getSize() - viewHeight){
					leftRow++;
					repaint();
				}
				break;

			case KeyEvent.VK_LEFT: 
				if(leftCol > 0){
					leftCol--;
					repaint();
				}
				break;

			case KeyEvent.VK_RIGHT: 
				if(leftCol < map.getSize() - viewLength){
					leftCol++;
					repaint();
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

	public void setSelectResource(boolean input){
		selectResource = input;
	}

	public void setMap(Map map) {
		this.map = map;

	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == buildSolarPlant) {
				currentSelection = 1;
			}
			if (e.getSource() == buildEngineering) {
				currentSelection = 2;
			}
			if (e.getSource() == buildMethanePlant) {
				currentSelection = 3;
			}
			if (e.getSource() == buildFarm) {
				currentSelection = 4;
			}
			//5 through 10 we can drop the currentSelection assignment
			//and just have the ItemBuildTask set here
			if (e.getSource() == buildBattery) {
				currentSelection = 5;
			}
			if (e.getSource() == buildRepairBox) {
				currentSelection = 6;
			}
			if (e.getSource() == buildGasTank) {
				currentSelection = 7;
			}
			if (e.getSource() == buildTreads) {
				currentSelection = 8;
			}
			if (e.getSource() == buildJetpack) {
				currentSelection = 9;
			}
			if (e.getSource() == buildBoat) {
				currentSelection = 10;
			}
			if (e.getSource() == collect) {
				currentSelection = 11;
			}
			if(e.getSource() == build) {
				System.out.println("clicks1:   " +clicks1);
				
				if(clicks1 == 0){
				buildSolarPlant.setEnabled(true);
				buildEngineering.setEnabled(true);
				buildMethanePlant.setEnabled(true);
				buildFarm.setEnabled(true);
				clicks1++;
				return;
				}
				if(clicks1 == 1){
					buildSolarPlant.setEnabled(false);
					buildEngineering.setEnabled(false);
					buildMethanePlant.setEnabled(false);
					buildFarm.setEnabled(false);
					clicks1--;
					return;
				}
			}
		}
		
	}
}
