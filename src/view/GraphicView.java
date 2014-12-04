package view;

import game.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GraphicView extends JPanel{

	private Map map;
	private int leftRow, leftCol;
	private int viewLength, viewHeight;
	private TextView textView;
	
	public GraphicView(Map map, int row, int col , int viewHeight, int viewLength, TextView textView) {

		this.setSize(1000,1000);
		this.setBackground(Color.BLACK);
		this.viewHeight = viewHeight;
		this.viewLength = viewLength;
		this.textView = textView;
		leftRow = row;
		leftCol = col;
		
		this.map = map;
		this.addKeyListener(new KeyMoveListener());
		
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

	public void setMap(Map map) {
		this.map = map;
		
	}
}
