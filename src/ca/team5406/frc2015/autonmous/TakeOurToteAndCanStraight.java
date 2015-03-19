package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeOurToteAndCanStraight extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;
	
	private Timer liftTimer;
	
	/*
	 * This auto mode will take our tote and can and move into the auto zone.
	 */
	
	public TakeOurToteAndCanStraight(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		super.routineInit();
		stacker.resetElevatorEncoder();
		stacker.setDesiredPostition(Stacker.StackerPositions.floorOpen);
		liftTimer = new Timer();
		System.out.println("AUTO: Taking our tote and can.");
	}
	
	public void routinePeriodic(){
		switch(super.autonState){
			default:
				break;
			case 0:
				stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
				liftTimer.reset();
				liftTimer.start();
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed || liftTimer.get() > 0.8){
					drivePID.initDriveToPos(2500);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.driveToPos()){
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					liftTimer.reset();
					super.autonState++;
				}
				break;
			case 3:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed || liftTimer.get() > 2.1){
					stacker.setDesiredPostition(Stacker.StackerPositions.oneUpClosed);
					liftTimer.reset();
					super.autonState++;
				}
				break;
			case 4:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.oneUpClosed || liftTimer.get() > 1.2){
					drivePID.initTurnToAngle(90);
					super.autonState++;
				}
				break;
			case 5:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(9000);
					drivePID.setSpeedLimit(0.6);
					super.autonState++;
				}
				break;
			case 6:
				if(drivePID.driveToPos()){
					super.autonState++;
					liftTimer.reset();
					stacker.setDesiredPostition(Stacker.StackerPositions.floorOpen);
				}
				break;
			case 7:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorOpen || liftTimer.get() > 1){
					super.autonState++;
					stacker.setDesiredPostition(Stacker.StackerPositions.oneUpOpen);
					liftTimer.reset();
				}
				break;
			case 8:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.oneUpOpen || liftTimer.get() > 1){
					super.autonState++;
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					liftTimer.reset();
				}
				break;
			case 9:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed || liftTimer.get() > 1){
					super.autonState++;
					routineEnd();
				}
				break;
			
		}		
	}
	
	public void routineEnd(){
		liftTimer.stop();
		super.isDone = true;
		System.out.println("AUTO: Done Auto Routine");
	}
	
}
