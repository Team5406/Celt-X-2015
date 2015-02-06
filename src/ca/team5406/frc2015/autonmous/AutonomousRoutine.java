package ca.team5406.frc2015.autonmous;

public class AutonomousRoutine {
	
	protected int autonState = 0;
	protected boolean isDone = false;
	
	public AutonomousRoutine(){
		
	}
		
	public int getAutonState(){
		return autonState;
	}
	
	public void routineInit(){
		autonState = 0;
	}
	
	public void routinePeriodic(){
		
	}
	
	public void routineEnd(){
		isDone = true;
	}

	public boolean isDone(){
		return isDone;
	}
	
}
