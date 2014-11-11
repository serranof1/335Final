package model;

import java.io.Serializable;

/**
 * This abstract class is used to make a serializable command for the purposes
 * of saving the game.
 * 
 * @author Gabe Serrano
 */
public abstract class Command<T> implements Serializable {
	private static final long serialVersionUID = -4838155228547508978L;

	/**
	 * Executes the command on the given argument
	 *
	 * @param executeOn
	 *            Object to execute command on
	 */
	public abstract void execute(T executeOn);

	/**
	 * Undoes the command's execution on the given object
	 *
	 * @param undoOn
	 *            Object to undo the command on
	 */
	public void undo(T undoOn) {
		// by default, commands cannot be undone
	}
}
