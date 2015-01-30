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
		elevatorMoving;
	}

	private StackerPositions currentStackerPosition;
	private StackerPositions desiredStackerPosition;
	private StackerPositions nextStackerPosition;
	private int stackerState = 0;
	
	public Stacker(Elevator elevator, Gripper gripper){
		this.elevator = elevator;
		this.gripper = gripper;
		desiredStackerPosition = StackerPositions.floorClosed;
		currentStackerPosition = StackerPositions.floorClosed;
		nextStackerPosition = null;
	}
	
	public void addToStack(){
		desiredStackerPosition = StackerPositions.floorClosed;
		nextStackerPosition = StackerPositions.upClosed;
	}
	
	public void setDesiredPostition(StackerPositions desiredPosition){
		desiredStackerPosition = desiredPosition;
		currentStackerPosition = StackerPositions.elevatorMoving;
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
						}
						else{
							desiredStackerPosition = null;
						}
						stackerState++;
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
