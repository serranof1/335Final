package view;

import game.Map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TextView extends JPanel{

	private JTextArea textView;
	private Map map;
	private int viewHeight, viewLength, leftRow, leftCol;
	
	/**
	 * @author Cody Jensen
	 * 
	 *  A mapView is a JPanel with an added JTextArea used for diplaying a textual representation of the map
	 */
	public TextView(Map map, int row, int col , int viewHeight, int viewLength) {

	
		this.map = map;
		this.setSize(800,800);
		this.setBackground(Color.WHITE);
		this.setLocation(0, 0);
		this.viewHeight = viewHeight;
		this.viewLength = viewLength;
		leftRow = row;
		leftCol = col;

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		int row = leftRow;
		int col = leftCol;
		
		
		for (int i = 0; i < viewHeight - 1; i++){
			for (int j = viewLength - 1; j >= 0; j--) { // Changed loop condition here.
				String charMap = "";
				charMap += "(";
				charMap += map.getTile(row, col).drawTextForm();
				charMap += " )";
				if(map.getTile(row, col).drawTextForm().equals("_") || map.getTile(row, col).drawTextForm().equals("m")){
					g.setColor(Color.RED);
				}
				if(map.getTile(row, col).drawTextForm().equals("M")){
					g.setColor(Color.GRAY);
				}
				if(map.getTile(row, col).drawTextForm().equals("~")){
					g.setColor(Color.BLUE);
				}
				if(map.getTile(row, col).drawTextForm().equals("@")){
					g.setColor(Color.BLACK);
				}
				g.setFont(new Font("TimesRoman", Font.BOLD, 18)); 
				g.drawString(charMap,(viewLength - j + 1) * 30, ((i + 1) * 30));
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

	public void setLeftRow(int i) {
		leftRow += i;
		
	}

	public void setLeftCol(int i) {
		leftCol += i;
		
	}

	public int getLeftRow() {
		
		return leftRow;
	}

	public int getLeftCol() {
		return leftCol;
	}
}

