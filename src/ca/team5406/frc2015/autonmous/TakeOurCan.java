package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeOurCan extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Drive drive;
	private Stacker stacker;
	private Timer liftTimer;
	
	/*
	 * This auto mode will take our can and move into the auto zone.
	 */
	
	public TakeOurCan(DrivePID drivePID, Stacker stacker, Drive drive){
		this.drivePID = drivePID;
		this.drive = drive;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		super.routineInit();
		
		liftTimer = new Timer();
		
		stacker.resetElevatorEncoder();
//		stacker.presetElevatorEncoder(Constants.elevatorOneUpPreset.getInt());
//		stacker.setDesiredPostition(Stacker.StackerPositions.oneUpOpen);
		System.out.println("AUTO: Taking our can");
	}
	
	public void routinePeriodic(){
		System.out.println(super.autonState + ":" + liftTimer.get());
		switch(super.autonState){
			default:
				break;
			case 0:
				stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
				liftTimer.start();
				super.autonState++;
				break;
			case 1:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.upClosed || liftTimer.get() > 1){
					drivePID.initDriveToPos(-11000);
					liftTimer.reset();
					drive.setPowerLeftRight(-0.5, -0.5);
					
					super.autonState++;
				}
				break;
			case 2:
				if(liftTimer.get() >= .6){
					super.autonState= 20;
					drive.setPowerLeftRight(0, 0);
					liftTimer.reset();
					stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
				}
				break;
			case 3:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.floorClosed || liftTimer.get() > 2){
					super.autonState++;
					stacker.setDesiredPostition(Stacker.StackerPositions.oneUpClosed);
				}
				break;
			case 4:
				if(stacker.getStackerPosition() == Stacker.StackerPositions.oneUpClosed || liftTimer.get() > 3){
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
		liftTimer.stop();
		System.out.println("AUTO: Done Auto Routine");
	}
	
}
