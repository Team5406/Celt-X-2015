package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.Timer;

public class Stacker {

	private Elevator elevator;
	private Gripper gripper;
	
	public static enum StackerPositions{
		floorOpen,
		floorClosed,
		upOpen,
		upClosed,
		carryClosed,
		manualControl,
		elevatorMoving;
	}

	private StackerPositions currentStackerPosition = StackerPositions.elevatorMoving;
	private StackerPositions desiredStackerPosition = StackerPositions.elevatorMoving;
	private StackerPositions nextStackerPosition = StackerPositions.elevatorMoving;
	private int stackerState = 0;
	
	public Stacker(Elevator elevator, Gripper gripper){
		this.elevator = elevator;
		this.gripper = gripper;
		desiredStackerPosition = StackerPositions.floorClosed;
		currentStackerPosition = StackerPositions.floorClosed;
		nextStackerPosition = StackerPositions.elevatorMoving;
	}
	
	public void addToStack(){
		desiredStackerPosition = StackerPositions.floorClosed;
		nextStackerPosition = StackerPositions.upClosed;
	}
	
	public void setDesiredPostition(StackerPositions desiredPosition){
		desiredStackerPosition = desiredPosition;
		currentStackerPosition = StackerPositions.elevatorMoving;
		nextStackerPosition = StackerPositions.elevatorMoving;
		stackerState = 0;
	}
	
	public String getStackerPositionName(){
		return currentStackerPosition.name();
	}
	
	public StackerPositions getStackerPosition(){
		return currentStackerPosition;
	}
	
	public void doAutoLoop(){
		switch(desiredStackerPosition){
			default:
				break;
			case floorOpen:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorFloorPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						gripper.setGripperExpansion(true);
						stackerState++;
						break;
					case 2:
						currentStackerPosition = StackerPositions.floorOpen;
						if(nextStackerPosition != null){
							desiredStackerPosition = nextStackerPosition;
							nextStackerPosition = null;
							stackerState = 0;
						}
						else{
							desiredStackerPosition = null;
						}
						break;
				}
				break;
			case floorClosed:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorFloorPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						gripper.setGripperExpansion(false);
						stackerState++;
						break;
					case 2:
						currentStackerPosition = StackerPositions.floorClosed;
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
						currentStackerPosition = StackerPositions.floorClosed;
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
			case carryClosed:
				switch(stackerState){
					default:
						break;
					case 0:
						gripper.setGripperExpansion(false);
						Timer.delay(0.2);//Terrible way to do this
						stackerState++;
						break;
					case 1:
						if(elevator.setElevatorPosition(Constants.elevatorCarryPreset.getInt())){
							stackerState++;
						}
						break;
					case 2:
						currentStackerPosition = StackerPositions.carryClosed;
						if(nextStackerPosition != null){
							desiredStackerPosition = nextStackerPosition;
							nextStackerPosition = null;
						}
						else{
							desiredStackerPosition = null;
							stackerState++;
						}
						break;
				}
		}
	}
	
}
