package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeOurToteAndCan extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;
	
	private Timer stateTimer;
	
	/*
	 * This auto mode will take our tote and can and move into the auto zone.
	 */
	
	public TakeOurToteAndCan(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		super.routineInit();
		
		stacker.setDesiredPostition(Stacker.StackerPositions.floorOpen);
		stateTimer = new Timer();
		System.out.println("AUTO: Taking our tote and can.");
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
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.driveToPos()){
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					super.autonState++;
				}
				break;
			case 3:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed){
					stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
					drivePID.initTurnToAngle(-90);
					super.autonState++;
				}
				break;
			case 4:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(-5000);
					super.autonState++;
				}
			case 5:
				if(drivePID.driveToPos()){
					drivePID.initDriveToPos(2500);
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
