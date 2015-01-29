package ca.team5406.frc2015.autonmous;

public class DoNothing extends AutonomousRoutine {
	
	public DoNothing(){
		super();
	}
	
	public void routinePeriodic(){
		System.out.println("AUTO: Doing Nothing");
		super.isDone = true;
	}
	
}
