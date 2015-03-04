package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeOurCan extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;
	
	private Timer stateTimer;
	
	/*
	 * This auto mode will take our can and move into the auto zone.
	 */
	
	public TakeOurCan(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		super.routineInit();

		stacker.presetElevatorEncoder(Constants.elevatorOneUpPreset.getInt());
		stacker.setDesiredPostition(Stacker.StackerPositions.oneUpOpen);
		stateTimer = new Timer();
		System.out.println("AUTO: Taking our can");
	}
	
	public void routinePeriodic(){
		switch(super.autonState){
			default:
				break;
			case 0:
				stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed){
					drivePID.initDriveToPos(-5000);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.driveToPos()){
					super.autonState++;
					routineEnd();
				}
				break;
			
		}		
	}
	
	public void routineEnd(){
		stateTimer.stop();
		super.isDone = true;
		System.out.println("AUTO: Done Auto Routine");
	}
	
}
