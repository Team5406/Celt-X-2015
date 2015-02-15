package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.Victor;

public class ToteRoller {
	private Victor toteMotor;
	
	public ToteRoller(){
		toteMotor= new Victor(6);
	}
	public void setSpeed(double setVal){
		toteMotor.set(setVal * -.20);		
	}
}
