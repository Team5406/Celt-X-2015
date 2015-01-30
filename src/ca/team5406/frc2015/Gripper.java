package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.Solenoid;

public class Gripper {
	
	private Solenoid gripperContraction;
	private Solenoid gripperExpansion;

	public Gripper(){
		gripperContraction = new Solenoid(2);
		gripperExpansion = new Solenoid(3);
	}
	
	public boolean getGripperExpanded(){
		return gripperExpansion.get();
	}
	
	public void setGripperExpansion(boolean expanded){
		gripperContraction.set(!expanded);
		gripperExpansion.set(expanded);
	}
	
}
