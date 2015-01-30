package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.Timer;

public class Stacker {

	private Elevator elevator;
	private Gripper gripper;
	
	public static enum StackerPositions{
		downOpen,
		upOpen,
		downClosed,
		upClosed,
		travelling;
	}
	
	private StackerPositions desiredStackerPosition;
	private StackerPositions currentStackerPosition;
	private StackerPositions nextStackerPosition;
	private int stackerState = 0;
	
	public Stacker(Elevator elevator, Gripper gripper){
		this.elevator = elevator;
		this.gripper = gripper;
		desiredStackerPosition = StackerPositions.downClosed;
		currentStackerPosition = StackerPositions.downClosed;
		nextStackerPosition = null;
	}
	
	public void addToStack(){
		desiredStackerPosition = StackerPositions.downClosed;
		nextStackerPosition = StackerPositions.upClosed;
	}
	
	public void setDesiredPostition(StackerPositions desiredPosition){
		desiredStackerPosition = desiredPosition;
		currentStackerPosition = StackerPositions.travelling;
		nextStackerPosition = null;
		stackerState = 0;
	}
	
	public String getStackerPosition(){
		return currentStackerPosition.name();
	}
	
	public void doAutoLoop(){
		switch(desiredStackerPosition){
			default:
				break;
			case downOpen:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorDownPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						gripper.setGripperExpansion(true);
						stackerState++;
						break;
					case 2:
						currentStackerPosition = StackerPositions.downOpen;
						if(nextStackerPosition != null){
							desiredStackerPosition = nextStackerPosition;
							nextStackerPosition = null;
						}
						else{
							desiredStackerPosition = null;
						}
						stackerState++;
						break;
				}
				break;
			case upOpen:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorUpPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						gripper.setGripperExpansion(true);
						stackerState++;
						break;
					case 2:
						currentStackerPosition = StackerPositions.upOpen;
						if(nextStackerPosition != null){
							desiredStackerPosition = nextStackerPosition;
							nextStackerPosition = null;
						}
						else{
							desiredStackerPosition = null;
						}
						stackerState++;
						break;
				}
				break;
			case downClosed:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorDownPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						gripper.setGripperExpansion(false);
						stackerState++;
						break;
					case 2:
						currentStackerPosition = StackerPositions.downClosed;
						if(nextStackerPosition != null){
							desiredStackerPosition = nextStackerPosition;
							nextStackerPosition = null;
						}
						else{
							desiredStackerPosition = null;
						}
						stackerState++;
						break;
				}
				break;
			case upClosed:
				switch(stackerState){
					default:
						break;
					case 0:
						gripper.setGripperExpansion(false);
						Timer.delay(0.2);//Terrible way of doing this but I'm lazy right now.
						stackerState++;
						break;
					case 1:
						if(elevator.setElevatorPosition(Constants.elevatorUpPreset.getInt())){
							stackerState++;
						}
						break;
					case 2:
						currentStackerPosition = StackerPositions.downClosed;
						if(nextStackerPosition != null){
							desiredStackerPosition = nextStackerPosition;
							nextStackerPosition = null;
						}
						else{
							desiredStackerPosition = null;
						}
						stackerState++;
						break;
				}
				break;
		}
	}
	
}
