package model;

public class DoNothingCommand extends Command<Drone> {
	private static final long serialVersionUID = -7848384872530724017L;

	/**
	 * Creates a doNothing command that every drone will default to when they
	 * are in the doNothing group of drones
	 */
	public DoNothingCommand() {
	}

	@Override
	public void execute(Drone executeOn) {
		// TODO Auto-generated method stub
		
	}
}