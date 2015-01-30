package ca.team5406.frc2015.autonmous;

import ca.team5406.frc2015.*;
import edu.wpi.first.wpilibj.Timer;

public class TakeBothOurs extends AutonomousRoutine {
	
	private DrivePID drivePID;
	private Stacker stacker;	
	
	private Timer stateTimer;
	
	/*
	 * This auto mode will stack our can on our tote and move them into the auto zone.
	 */
	
	public TakeBothOurs(DrivePID drivePID, Stacker stacker){
		this.drivePID = drivePID;
		this.stacker = stacker;
	}
	
	public void routineInit(){
		stacker.setGripperExpansion(true);
		stacker.setElevatorUp(false);
		stateTimer = new Timer();
		System.out.println("Taking our things");
	}
	
	public void routinePeriodic(){
		switch(super.autonState){
			default:
				break;
			case 0:
				stacker.setElevatorUp(true);
				stateTimer.reset();
				stateTimer.start();
				super.autonState++;
				break;
			case 1:
				if(stateTimer.get() >= Constants.autoAddToStackRaiseDelay.getDouble()){
					stateTimer.stop();
					drivePID.initDriveToPos(0);
					super.autonState++;
				}
				break;
			case 2:
				if(drivePID.driveToPos()){
					stacker.doAutoAddToStack(true);
					super.autonState++;
				}
				break;
			case 3:
				if(stacker.getAutoStackState() == 0){
					drivePID.initTurnToAngle(90);
					super.autonState++;
				}
				break;
			case 4:
				if(drivePID.turnToAngle()){
					drivePID.initDriveToPos(0);
					super.autonState++;
				}
				break;
			case 5:
				if(drivePID.driveToPos()){
					stacker.setElevatorUp(false);
					super.autonState++;
				}
				break;
			case 6:
				if(stacker.getElevatorDown()){
					stacker.setGripperExpansion(true);
					drivePID.initDriveToPos(0);
					super.autonState++;
				}
				break;
			case 7:
				if(drivePID.driveToPos()){
					super.autonState++;
					routineEnd();
				}
				break;
			
		}		
		stacker.doAutoAddToStack(false);
	}
	
	public void routineEnd(){
		stateTimer.stop();
		super.isDone = true;
		System.out.println("AUTO: Done Auto Routine");
	}
	
}
