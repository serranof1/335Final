package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class StockpilePanel extends JPanel{
	private int electricity;
	private int methane;
	private int iron;
	private int carbon;
	
	private JTextField resources;
	
	public StockpilePanel() {
		
		setLayout(new GridLayout(1, 3));
		setBackground(Color.RED);
		setSize(new Dimension(1200, 50));
		setPreferredSize(this.getSize());
		resources = new JTextField();
	}

	public void drawResources(String input){
		resources.setText(input);
	}
}
