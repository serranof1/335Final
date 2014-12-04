package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTabbedPane;

public class floatingPanel extends JTabbedPane {

	public floatingPanel() {
		this.setSize(200,200);
		this.setLocation(200, 200);
		this.setBackground(Color.BLUE);
	}

	/**
	 * This is where we tell the mouse what to do on clicks and movement
	 * @author Cody Jensen
	 *
	 */
	private class ListenToMouse implements MouseMotionListener, MouseListener {
	
		public void mouseClicked(MouseEvent evt) {
	
		}

		public void mouseMoved(MouseEvent evt) {

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
			setLocation(evt.getX(), evt.getY());
		}
	}
}
