package model;

import java.util.LinkedList;

public class Drone {
	/**
	 * Private instance variable that will be use to determine if the drone is
	 * currently doing anything. The default will be the doNothing command
	 * 
	 * @author Gabe Serrano
	 */
	private Command currentJob;
	/**
	 * Private linked list to keep track of the specific drones commands. As the
	 * drone gets more and more things to do if it needs power it will start
	 * moving further and further up the list until it is first then it will
	 * stop what its doing and go get power.
	 */
	private LinkedList<Command> taskList;
	/**
	 * Keeps track of the drone's current power level. Depletes over time by
	 * some factor (-.5?) when in standby and when doing tasks increases the
	 * factor (-1.0?) This will also be used for charging at the station(+.75?).
	 * Will set max power to be a certain number (100?) but can later be
	 * upgraded.
	 * 
	 */
	private double power;
	/**
	 * Keeps track of whether the drone is charging
	 */
	private boolean charging;

	public Drone(double power, Command defaultJob) {
		this.power = power;
		this.currentJob = defaultJob;
		this.charging = false;
	}

	private boolean isCharging() {
		if (charging)
			return true;
		else
			return false;
	}

	public void doNothing() {
		// TODO Auto-generated method stub
	}

	/**
	 * This code will be executed depending on when the drones' power updates.
	 * It will either increment or decrement depending on different conditions.
	 */
	public void endOfLoopUpdate() {
		if (charging)
			power += .75;
		else
			power--;
	}
}