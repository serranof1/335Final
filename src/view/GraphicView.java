package view;

import game.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

public class GraphicView extends JPanel{


	private LinkedList<BufferedImage> images = new LinkedList<BufferedImage>();
	private Map map;
	private int leftRow, leftCol;
	private int viewLength, viewHeight;
	public GraphicView(Map map, int row, int col , int viewHeight, int viewLength) {

		this.setSize(800,800);
		this.setBackground(Color.BLACK);
		this.viewHeight = viewHeight;
		this.viewLength = viewLength;
		leftRow = row;
		leftCol = col;
		
		this.map = map;
		

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
		System.out.println("MAP SIZE:  "+map.getSize());
		
		for (int i = 0; i < viewHeight - 1; i++){
			for (int j = viewLength - 1; j >= 0; j--) { // Changed loop condition here.
				map.getTile(row, col).draw(g, (viewLength - j), i);
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
}
