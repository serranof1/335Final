package view;

import game.MainGame;
import game.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import buildings.Base;

public class GraphicView extends JPanel{

	private Map map;
	private int leftRow, leftCol;
	private int viewLength, viewHeight;

	private int startPointX, startPointY, endPointY, endPointX;
	private int clicks = 0;

	private JButton button, collect, makeDrone;
	private JPanel userInfo, stockPileInfo;

	private boolean selectResource;
	public boolean dragging;
	
	private MainGame mainGame;

	public GraphicView(MainGame mainGame, int row, int col , int viewHeight, int viewLength) {
		this.mainGame = mainGame;
		this.setBackground(Color.BLACK);
		this.viewHeight = viewHeight;
		this.viewLength = viewLength;
		this.setLayout(new BorderLayout());
		userInfo = new JPanel();
		userInfo.setBackground(Color.WHITE);
		stockPileInfo = new JPanel();
		stockPileInfo.setBackground(Color.RED);
		stockPileInfo.setPreferredSize(new Dimension(1200, 50));

		userInfo.setLayout(new GridBagLayout());
		this.add(userInfo, BorderLayout.EAST);
		this.add(stockPileInfo, BorderLayout.SOUTH);
		
		GridBagConstraints c = new GridBagConstraints();
		
		makeDrone = new JButton("New Drone");
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		userInfo.add(button, c);

		button = new JButton("Button 2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		userInfo.add(button, c);

		button = new JButton("Button 3");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		userInfo.add(button, c);

		collect = new JButton("Collect");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		userInfo.add(collect, c);

		button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.EAST; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 2;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		userInfo.add(button, c);

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

	//We may want to use that JViewer or whatever it was Gabe was talking about, but that's later.
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		int row = leftRow;
		int col = leftCol;

		for (int i = 0; i < viewHeight; i++){
			for (int j = viewLength; j > 0; j--) { // Changed loop condition here.

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
		this.addKeyListener(new KeyMoveListener());
	}

	private class CollectListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				setSelectResource(true);
			}
	}
	private class MakeDroneListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				mainGame.createDrone();
			}
	}
	/**
	 * This is where we tell the mouse what to do on clicks and movement
	 * @author Cody Jensen
	 *
	 */
	private class ListenToMouse implements MouseMotionListener, MouseListener {

		public void mouseClicked(MouseEvent evt) {

			if(selectResource && clicks == 0){
				startPointX = evt.getX();
				startPointY = evt.getY();
				clicks++;
				System.out.println("mouse click 1:  " +clicks);

			} else if(selectResource && clicks == 1){
				System.out.println("mouse click 2:   " +clicks);
				clicks--;
				selectResource = false;
				
				grabFocus();
			} else {
				//do other things here, im not sure what
			}
		}

		public void mouseMoved(MouseEvent evt) {
			endPointX = evt.getX();
			endPointY = evt.getY();
			if(selectResource && clicks == 1)

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
}
