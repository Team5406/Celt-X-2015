package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.Timer;

public class Stacker {

	private Elevator elevator;
	private Gripper gripper;
	private CanHolder holder;
	
	public static enum StackerPositions{
		floorOpen,
		floorClosed,
		floor,
		upOpen,
		upClosed,
		up,
		carryClosed,
		carry,
		oneUpOpen,
		oneUpClosed,
		oneUp,
		manualControl,
		elevatorMoving,
		nothing;
		
	}

	private StackerPositions currentStackerPosition = StackerPositions.elevatorMoving;
	private StackerPositions desiredStackerPosition = StackerPositions.elevatorMoving;
	private StackerPositions nextStackerPosition = StackerPositions.elevatorMoving;
	private int stackerState = 0;
	
	public Stacker(Elevator elevator, Gripper gripper, CanHolder holder){
		this.elevator = elevator;
		this.gripper = gripper;
		this.holder = holder;
		desiredStackerPosition = StackerPositions.floorClosed;
		currentStackerPosition = StackerPositions.floorClosed;
		nextStackerPosition = StackerPositions.elevatorMoving;
	}
	
	public void addToStack(){
		setDesiredPostition(StackerPositions.floorClosed);
		nextStackerPosition = StackerPositions.upClosed;
	}
	
	public void setDesiredPostition(StackerPositions desiredPosition){
		desiredStackerPosition = desiredPosition;
		currentStackerPosition = StackerPositions.elevatorMoving;
		stackerState = 0;
	}
	
	public String getStackerPositionName(){
		return currentStackerPosition.name();
	}
	
	public StackerPositions getStackerPosition(){
		return currentStackerPosition;
	}
	
	public StackerPositions getDesiredPosition(){
		return desiredStackerPosition;
	}
	
	public void presetElevatorEncoder(int pos){
		elevator.presetElevatorEncoder(pos);
	}
	
	public void resetElevatorEncoder(){
		elevator.resetEncoder();
	}
	
	public void stopElevator(){
		elevator.setBrake(true);
		elevator.setElevatorSpeed(0.0);
	}
	
	private void finishMoving(){
		if(nextStackerPosition != StackerPositions.nothing){
			setDesiredPostition(nextStackerPosition);
			nextStackerPosition = StackerPositions.nothing;
		}
		else{
			setDesiredPostition(StackerPositions.nothing);
		}
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
						finishMoving();
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
						finishMoving();
						break;
				}
				break;
			case floor:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorFloorPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						currentStackerPosition = StackerPositions.floor;
						finishMoving();
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
						finishMoving();
						break;
				}
				break;
			case upClosed:
				switch(stackerState){
					default:
						break;
					case 0:
						holder.setPosition(false);
						gripper.setGripperExpansion(false);
						Timer.delay(0.25);//Terrible way of doing this but I'm lazy right now.
						stackerState++;
						break;
					case 1:
						if(elevator.setElevatorPosition(Constants.elevatorUpPreset.getInt())){
							holder.setPosition(true);
							stackerState++;
						}
						break;
					case 2:
						currentStackerPosition = StackerPositions.upClosed;
						finishMoving();
						break;
				}
				break;
			case up:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorUpPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						currentStackerPosition = StackerPositions.up;
						finishMoving();
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
						finishMoving();
						break;
				}
				break;
			case carry:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorCarryPreset.getInt())){
							stackerState++;
						}
						break;
					case 2:
						currentStackerPosition = StackerPositions.carry;
						finishMoving();
						break;
				}
				break;
			case oneUpOpen:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorOneUpPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						gripper.setGripperExpansion(true);
						stackerState++;
						break;
					case 2:
						currentStackerPosition = StackerPositions.oneUpOpen;
						finishMoving();
						break;
				}
				break;
			case oneUpClosed:
				switch(stackerState){
					default:
						break;
					case 0:
						gripper.setGripperExpansion(false);
						Timer.delay(0.2);//Terrible way of doing this but I'm lazy right now.
						stackerState++;
						break;
					case 1:
						if(elevator.setElevatorPosition(Constants.elevatorOneUpPreset.getInt())){
							stackerState++;
						}
						break;
					case 2:
						currentStackerPosition = StackerPositions.oneUpClosed;
						finishMoving();
						break;
				}
				break;
			case oneUp:
				switch(stackerState){
					default:
						break;
					case 0:
						if(elevator.setElevatorPosition(Constants.elevatorOneUpPreset.getInt())){
							stackerState++;
						}
						break;
					case 1:
						currentStackerPosition = StackerPositions.oneUpOpen;
						finishMoving();
						break;
				}
				break;
		}
	}
	
}
