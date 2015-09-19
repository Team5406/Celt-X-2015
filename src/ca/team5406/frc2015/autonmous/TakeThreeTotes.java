package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeThreeTotes extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;
	
	private Timer stateTimer;
	
	/*
	 * This auto mode will stack all 3 totes and take them to the auto zone.
	 */
	
	public TakeThreeTotes(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		super.routineInit();
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
				stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.carryClosed){
					drivePID.initTurnToAngle(45);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 3:
				if(drivePID.driveToPos()){
					drivePID.initTurnToAngle(-45);
					super.autonState++;
				}
				break;
			case 4:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 5:
				if(drivePID.driveToPos()){
					drivePID.initTurnToAngle(45);
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					super.autonState++;
				}
				break;
			case 6:
				if(drivePID.turnToAngle() && stacker.getStackerPosition() == Stacker.StackerPositions.upClosed){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 7:
				if(drivePID.driveToPos()){
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					super.autonState++;
				}
				break;
			case 8:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed){
					stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
					super.autonState++;
				}
				break;
			case 9:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.carryClosed){
					drivePID.initTurnToAngle(45);
					super.autonState++;
				}
				break;
			case 10:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 11:
				if(drivePID.driveToPos()){
					drivePID.initTurnToAngle(-45);
					super.autonState++;
				}
				break;
			case 12:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 13:
				if(drivePID.driveToPos()){
					drivePID.initTurnToAngle(45);
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					super.autonState++;
				}
				break;
			case 14:
				if(drivePID.turnToAngle() && stacker.getStackerPosition() == Stacker.StackerPositions.upClosed){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 15:
				if(drivePID.driveToPos()){
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					super.autonState++;
				}
				break;
			case 16:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed){
					stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
					super.autonState++;
				}
				break;
			case 17:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.carryClosed){
					drivePID.initTurnToAngle(45);
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
					drivePID.initTurnToAngle(-45);
					super.autonState++;
				}
				break;
			case 20:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 21:
				if(drivePID.driveToPos()){
					drivePID.initTurnToAngle(45);
					stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
					super.autonState++;
				}
				break;
			case 22:
				if(drivePID.turnToAngle() && stacker.getStackerPosition() == Stacker.StackerPositions.upClosed){
					drivePID.initDriveToPos(1000);
					super.autonState++;
				}
				break;
			case 23:
				if(drivePID.driveToPos()){
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
					super.autonState++;
				}
				break;
			case 24:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed){
					stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
					drivePID.initTurnToAngle(90);
					super.autonState++;
				}
				break;
			case 25:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(3000);
					super.autonState++;
				}
				break;
			case 26:
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
