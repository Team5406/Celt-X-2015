package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.DrivePID;

public class MoveToZone extends AutonomousRoutine {
	
	private DrivePID drivePID;
	
	public MoveToZone(DrivePID drivePID){
		this.drivePID = drivePID;
	}
	
	public void routineInit(){
		System.out.println("AUTO: Moving to auto zone");
		drivePID.initDriveToPos(0);
	}
	
	public void routinePeriodic(){
		if(drivePID.driveToPos()){
			super.routineEnd();
		}
	}
	
}