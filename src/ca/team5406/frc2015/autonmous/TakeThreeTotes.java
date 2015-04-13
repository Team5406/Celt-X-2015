package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeThreeTotes extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;
	private CanHolder holder;
	
	private Timer liftTimer;
	
	private Timer stateTimer;
	
	/*
	 * This auto mode will stack all 3 totes and take them to the auto zone.
	 */
	
	public TakeThreeTotes(DrivePID drivePID, Stacker stacker, CanHolder holder){
		this.drivePID = drivePID;
		this.stacker = stacker;
		this.holder = holder;
	}
	
	public void routineInit(){
		super.routineInit();
		liftTimer = new Timer();
		stacker.resetElevatorEncoder();
		stacker.setDesiredPostition(Stacker.StackerPositions.floorOpen);
		stateTimer = new Timer();
		System.out.println("AUTO: Taking all of the totes.");
	}
	
	public void routinePeriodic(){
		switch(super.autonState){
			default:
				break;
			case 0:
				holder.setPosition(true);
				liftTimer.start();
				stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed || liftTimer.get() > 1.0){
					drivePID.initDriveToPos(4000);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.driveToPos()){
					liftTimer.reset();
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					super.autonState++;
				}
				break;
			case 3:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed || liftTimer.get() > 1.5){
					liftTimer.reset();
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					super.autonState++;
				}
				break;
			case 4:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed || liftTimer.get() > 1.0){
					holder.setPosition(false);
					drivePID.initTurnToAngle(185);
					super.autonState++;
				}
				break;
			case 5:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(2200);
					super.autonState++;
				}
				break;
			case 6:
				if(drivePID.driveToPos()){
					holder.setPosition(false);
					liftTimer.reset();
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					super.autonState = 100;
				}
				break;
			case 7:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed || liftTimer.get() > 1.0){
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					super.autonState++;
				}
				break;
			case 8:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed){
					holder.setPosition(false);
					drivePID.initTurnToAngle(-30);
					super.autonState++;
				}
				break;
			case 9:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 10:
				if(drivePID.driveToPos()){
					drivePID.initTurnToAngle(60);
					super.autonState++;
				}
				break;
			case 11:
				if(drivePID.turnToAngle()){
					drivePID.initTurnToAngle(-60);
					super.autonState++;
				}
				break;
			case 12:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(-1000);
					super.autonState++;
				}
				break;
			case 13:
				if(drivePID.driveToPos()){
					drivePID.initTurnToAngle(30);
					super.autonState++;
				}
				break;
			case 14:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 15:
				if(drivePID.driveToPos()){
					holder.setPosition(true);
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					super.autonState++;
				}
				break;
			case 16:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed){
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					super.autonState++;
				}
				break;
			case 17:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed){
					drivePID.initTurnToAngle(-90);
					super.autonState++;
				}
				break;
			case 18:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 19:
				if(drivePID.driveToPos()){
					stacker.setDesiredPostition(Stacker.StackerPositions.floorOpen);
					super.autonState++;
				}
				break;
			case 20:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorOpen){
					drivePID.initDriveToPos(-500);
					super.autonState++;
				}
				break;
			case 21:
				if(drivePID.driveToPos()){
					routineEnd();
					stacker.stopElevator();
					super.autonState++;
				}
				break;
		}
	}
	
	public void routineEnd(){
		stateTimer.stop();
		liftTimer.stop();
		super.isDone = true;
		System.out.println("AUTO: Done Auto Routine");
	}
	
}
