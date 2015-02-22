package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeOurTote extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;
	
	private Timer stateTimer;
	
	/*
	 * This auto mode will stack our can on our tote and move them into the auto zone.
	 */
	
	public TakeOurTote(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		super.routineInit();
		
		stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
		stateTimer = new Timer();
		System.out.println("Taking our can");
	}
	
	public void routinePeriodic(){
		switch(super.autonState){
			default:
				break;
			case 0:
				stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.carryClosed){
					drivePID.initTurnToAngle(-90);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(-5000);
					super.autonState++;
				}
			case 3:
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
