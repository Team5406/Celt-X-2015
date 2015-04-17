package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class SetUpForNoodleRight extends AutonomousRoutine{
	private DrivePID drivePID;
	private Stacker stacker;
	
	private Timer liftTimer;
	/*
	 * this auto will take our can and set up for noodle to the right of the ds
	 */

	public SetUpForNoodleRight(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		liftTimer = new Timer();
		super.routineInit();
		//stacker.presetElevatorEncoder(Constants.elevatorOneUpPreset.getInt());
		stacker.setDesiredPostition(Stacker.StackerPositions.floorOpen);
		System.out.println("AUTO: Getting ready for noodle right.");
	}
	
	public void routinePeriodic(){
		switch(super.autonState){
			default:
				break;
			case 0:
				stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
				liftTimer.start();
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed){
					liftTimer.stop();
					drivePID.initTurnToAngle(15);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(3000);
					super.autonState++;
				}
				break;
			case 3:
				if(drivePID.driveToPos()){
					super.autonState++;
					liftTimer.reset();
					liftTimer.start();
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
				}
				break;
			case 4:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed || liftTimer.get() > 2.0){
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					liftTimer.reset();
					super.autonState++;
				}
				break;
			case 5:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed || liftTimer.get() > 1.0){
					liftTimer.stop();
					stacker.stopElevator();
					super.autonState++;
					routineEnd();
				}
				break;			
		}	
	}

	public void routineEnd(){
		super.isDone = true;
		System.out.println("AUTO: Done Auto Routine");
	}
	
}
