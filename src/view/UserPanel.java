package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UserPanel extends JPanel {

	JButton buildButton;
	GraphicView view;
	
	public UserPanel(GraphicView view) {
		this.setSize(200,200);
		this.setPreferredSize(this.getSize());
		this.setBackground(Color.BLUE);
		this.setLayout(new GridLayout(4,1));
		this.view = view;
		
		setupButtons();
		setupListeners();
	}

	private void setupButtons(){
		buildButton = new JButton("BUILD");
		
		
		this.add(buildButton);
	}

	private void setupListeners(){
		buildButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				view.setSelectResource(true);
				
			}
		});
	}
}
