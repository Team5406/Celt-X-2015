package ca.team5406.frc2015.autonmous;

public class DoNothing extends AutonomousRoutine {
	
	public DoNothing(){
		super();
	}
	
	public void routineInit(){
		super.routineInit();
		System.out.println("AUTO: Doing nothing.");
	}
	
	public void routinePeriodic(){
		System.out.println("AUTO: Doing Nothing");
		routineEnd();
	}
	
	public void routineEnd(){
		super.isDone = true;
		System.out.println("AUTO: Done Auto Routine");
	}
}
