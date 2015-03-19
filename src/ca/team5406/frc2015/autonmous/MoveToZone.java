package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.DrivePID;

public class MoveToZone extends AutonomousRoutine {
	
	private DrivePID drivePID;
	
	public MoveToZone(DrivePID drivePID){
		this.drivePID = drivePID;
	}
	
	public void routineInit(){
		super.routineInit();
		
		System.out.println("AUTO: Moving to auto zone");
		drivePID.initDriveToPos(-11000);
	}
	
	public void routinePeriodic(){
		if(drivePID.driveToPos()){
			routineEnd();
		}
	}
	
	public void routineEnd(){
		super.isDone = true;
		System.out.println("AUTO: Done auto routine.");
	}
	
}
