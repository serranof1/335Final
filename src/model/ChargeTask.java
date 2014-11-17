package model;


public class ChargeTask extends Task{

	public ChargeTask(Drone drone ) {
		super(drone);
		
	}

	@Override
	public void execute() {
		if(nextToPower()){
			System.out.println("Drone is next to power Supply");
			if(drone.getPower()<100){
				drone.setPower(drone.getPower()+10);
				System.out.println("Incremented Charge by 10");
				if(drone.getPower()< 200){
					drone.getTaskList().push(new ChargeTask(drone));
				}else{
					System.out.println("Done Charging");
					drone.setPower(100);
				}
			}
		}else{
			System.out.println("Moving To power supply!");
			moveToPower();
		}
	}

	private boolean nextToPower() {
		// TODO Auto-generated method stub
		return true;
	}

	private void moveToPower() {
		// TODO Auto-generated method stub
		
	}
}
