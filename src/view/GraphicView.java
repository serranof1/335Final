package view;

import game.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GraphicView extends JPanel{

	private Map map;
	private int leftRow, leftCol;
	private int viewLength, viewHeight;

	private int startPointX, startPointY, endPointY, endPointX;
	private int clicks = 0;
	
	private TextView textView;
	private boolean selectResource;
	public boolean dragging;

	public GraphicView(Map map, int row, int col , int viewHeight, int viewLength, TextView textView) {

		this.setSize(1000,1000);
		this.setBackground(Color.BLACK);
		this.viewHeight = viewHeight;
		this.viewLength = viewLength;
		this.textView = textView;
		leftRow = row;
		leftCol = col;
		selectResource = true;
		this.map = map;
		this.addKeyListener(new KeyMoveListener());
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

				map.getTile(col, row).draw(g, (viewLength - j) * 50, i * 50);
				col++;
			}
			col = leftCol;
			row++;
		}

		if(selectResource && clicks == 1){
			int topLeftX = startPointX / 50;
			int topLeftY = startPointY / 50;
			int bottomRightX = endPointX / 50;
			int bottomLeftY = endPointY / 50;
			Color myGreen = new Color(0 , 255 , 0, 125);
			System.out.println("drawing a rectangle:  "+topLeftX +"  " +topLeftY);
			g.setColor(myGreen);
			g.fillRect(topLeftX * 50, topLeftY * 50, (bottomRightX - topLeftX) * 50, (bottomLeftY - topLeftY) * 50);
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

		this.addMouseMotionListener(motionListener);
		this.addMouseListener(listener);		
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
			} else {
				//do other thigns here, im not sure what
			}
		}

		public void mouseMoved(MouseEvent evt) {
			endPointX = evt.getX();
			endPointY = evt.getY();
			if(selectResource && clicks == 1)
				System.out.println("moving");
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
					textView.setLeftRow(-1);

					textView.repaint();
					repaint();
				} 
				break;

			case KeyEvent.VK_DOWN: 
				if(leftRow < map.getSize() - viewHeight){
					leftRow++;
					textView.setLeftRow(1);
					textView.repaint();
					repaint();
				}
				break;

			case KeyEvent.VK_LEFT: 
				if(leftCol > 0){
					leftCol--;
					textView.setLeftCol(-1);
					textView.repaint();
					repaint();
				}
				break;

			case KeyEvent.VK_RIGHT: 
				if(leftCol < map.getSize() - viewLength){
					leftCol++;
					textView.setLeftCol(1);
					textView.repaint();
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

	public void setSelectResource(){
		
	}
	
	public void setMap(Map map) {
		this.map = map;

	}
}
