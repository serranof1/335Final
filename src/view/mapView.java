package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class mapView extends JPanel{

	private JTextArea textView;
	/**
	 * @author Cody Jensen
	 * 
	 *  A mapView is a JPanel with an added JTextArea used for diplaying a textual representation of the map
	 */
	public mapView() {

		textView = new JTextArea();

		this.setSize(800,800);
		this.setBackground(Color.WHITE);
		this.setLocation(50, 50);
		textView.setLocation(20, 20);
		textView.setFont(new Font("Serif", Font.BOLD, 20));
		this.add(textView);

	}

	public void setTextArea(String input){
		
		textView.setText(input);
	}
}
