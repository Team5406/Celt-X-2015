package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.Solenoid;

public class CanHolder {
	private Solenoid canHolderContraction;
	private Solenoid canHolderExpansion;
	
	private Elevator elevator;
	private Gripper gripper;
	
	private boolean desiredOpen = true;
	
	public CanHolder(Elevator elevator, Gripper gripper){
		canHolderContraction = new Solenoid(Constants.canHolderContraction.getInt());
		canHolderExpansion = new Solenoid(Constants.canHolderExpansion.getInt());
		setPosition(true);
		
		this.elevator = elevator;
		this.gripper = gripper;
	}
	
	public boolean getHolderOpen(){
		return canHolderExpansion.get();
	}
	
	public void setPosition(boolean expanded){
		canHolderContraction.set(!expanded);
		canHolderExpansion.set(expanded);
	}
	
	public void setDesiredOpen(boolean open){
		desiredOpen = open;
	}
	
	public void doAutoLoop(){
		if(gripper.getGripperExpanded() || desiredOpen){
			setPosition(true);
		}
		else if(!desiredOpen && (!elevator.getElevatorMoving() || elevator.getElevatorPosition() < 0)){
			setPosition(false);
		}
		else setPosition(true);
	}
	
}
