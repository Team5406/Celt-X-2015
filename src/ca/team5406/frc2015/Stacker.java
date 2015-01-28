package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class Stacker {

	private Solenoid elevatorUpSolenoid = new Solenoid(0);
	private Solenoid elevatorDownSolenoid = new Solenoid(1);
	private Solenoid grabberExpansion = new Solenoid(2);
	private Solenoid grabberContraction = new Solenoid(3);
	
	private DigitalInput elevatorDownSensor = new DigitalInput(4);
	
	private int autoAddToStackState = 0;
	private Timer autoAddToStackTimer = new Timer();
	
	public Stacker(){
		
	}
	
	public void setElevatorUp(boolean up){
		elevatorDownSolenoid.set(!up);
		elevatorUpSolenoid.set(up);
	}
	
	public boolean getElevatorDown(){
		return elevatorDownSensor.get();
	}
	
	public void setGripperExpansion(boolean expanded){
		grabberExpansion.set(expanded);
		grabberContraction.set(!expanded);
	}
	
	public int getAutoStackState(){
		return autoAddToStackState;
	}
	
	public int doAutoAddToStack(boolean manualButton){
		System.out.println("Timer: " + autoAddToStackTimer.get());
		System.out.println("Stste: " + autoAddToStackState);

		switch(autoAddToStackState){
			default:
				return -1;
			case 0:
				if(manualButton){
					autoAddToStackState++;
					autoAddToStackTimer.reset();
					autoAddToStackTimer.start();
				}
				break;
			case 1:
				setElevatorUp(false);
				if(autoAddToStackTimer.get() >= Constants.autoAddToStackRaiseDelay.getDouble()){
					autoAddToStackState++;
					autoAddToStackTimer.reset();
					autoAddToStackTimer.start();
				}
				break;
			case 2:
				setGripperExpansion(false);
				if(autoAddToStackTimer.get() >= 0.5){
					autoAddToStackState++;
				}
				break;
			case 3:
				setElevatorUp(true);
				autoAddToStackTimer.stop();
				autoAddToStackState = 0;
				break;
		}
		return autoAddToStackState;
	}
	
	
	
}
