package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class StockpilePanel extends JPanel{
	private int electricity;
	private int methane;
	private int iron;
	private int carbon;
	
	
	private JTextField resources, updateField;
	
	public StockpilePanel() {
		
		setLayout(new GridLayout(2, 1));
		setBackground(Color.RED);
		setSize(new Dimension(1200, 50));
		setPreferredSize(this.getSize());
		resources = new JTextField();
		updateField = new JTextField();
		add(updateField);
		add(resources);
	}

	public void drawUpdateField(String input){
		Font font = new Font("SansSerif", Font.BOLD, 20);
		updateField.setFont(font);
		updateField.setHorizontalAlignment(JTextField.CENTER);
		updateField.setText(input);
	}
	
	public void drawResources(String input){
		Font font = new Font("SansSerif", Font.BOLD, 20);
		resources.setHorizontalAlignment(JTextField.CENTER);
		resources.setFont(font);
		resources.setText(input);
	}
}
