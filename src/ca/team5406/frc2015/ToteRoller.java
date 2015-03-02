package ca.team5406.frc2015;

import ca.team5406.util.Toggle;
import edu.wpi.first.wpilibj.Victor;

public class ToteRoller {
	private Victor toteMotor;
	private Toggle shouldBeOn;
	
	public ToteRoller(){
		toteMotor= new Victor(Constants.toteStopperVictor.getInt());
		shouldBeOn = new Toggle(false);
	}
	
	public void toggleState(){
		shouldBeOn.toggle();
	}
	
	public void startToteRoller(){
		shouldBeOn.setTrue();
	}

	public void stopToteRoller(){
		shouldBeOn.setFalse();
	}
	
	public boolean getStatus(){
		return shouldBeOn.get();
	}
	
	public void doToteRoller(){
		if(shouldBeOn.get()){
			toteMotor.set(Constants.rollerSpeed.getDouble());
		}
		else{
			toteMotor.set(0);
		}
	}
}
