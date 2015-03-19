package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeOurTote extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;
	
	private Timer liftTimer;
	
	/*
	 * This auto mode will take our tote and move into the auto zone.
	 */
	
	public TakeOurTote(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		super.routineInit();
		liftTimer = new Timer();
		stacker.resetElevatorEncoder();
		stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
		System.out.println("AUTO: Taking our tote");
	}
	
	public void routinePeriodic(){
		switch(super.autonState){
			default:
				break;
			case 0:
				stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
				liftTimer.start();
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.carryClosed || liftTimer.get() > 0.8){
					drivePID.initDriveToPos(-11000);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.driveToPos()){
					super.autonState++;
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					liftTimer.reset();
				}
				break;
			case 3:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed || liftTimer.get() > 1){
					super.autonState++;
					routineEnd();
				}
			
		}		
	}
	
	public void routineEnd(){
		super.isDone = true;
		System.out.println("AUTO: Done Auto Routine");
	}
	
}
