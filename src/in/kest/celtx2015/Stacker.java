package in.kest.celtx2015;

import edu.wpi.first.wpilibj.Solenoid;

public class Stacker {

	public Solenoid elevatorUpSolenoid = new Solenoid(0);
	public Solenoid elevatorDownSolenoid = new Solenoid(1);
	public Solenoid grabberExpansion = new Solenoid(2);
	public Solenoid grabberContraction = new Solenoid(3);
	
	private int autoStackState = 0;
	
	public Stacker(){
		
	}
	
	public void setElevatorUp(boolean up){
		elevatorDownSolenoid.set(!up);
		elevatorUpSolenoid.set(up);
	}
	
	public void setGripperExpansion(boolean expanded){
		grabberExpansion.set(expanded);
		grabberContraction.set(!expanded);
	}
	
	public int doAutoStack(){
		switch(autoStackState){
			default:
				return -1;
			case 0:
				break;
		}
		return autoStackState;
	}
	
	
	
}
