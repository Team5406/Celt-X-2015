package ca.team5406.util.controllers;

import edu.wpi.first.wpilibj.Joystick;

public class Controller extends Joystick {

	int numButtons = 10;
	boolean[] previousButtonState = new boolean[numButtons + 1];
	
	public Controller(int port) {
		super(port);
	}
	
	public void updateButtons(){
		for(int i = 1; i <= numButtons; i++){
			previousButtonState[i] = getRawButton(i);
		}
	}
	
	public boolean getButtonHeld(int button){
		return super.getRawButton(button);
	}
	
	public boolean getButtonOnce(int button){
		return (super.getRawButton(button) && !previousButtonState[button]);
	}
	
	public boolean getButtonRelease(int button){
		return (!super.getRawButton(button) && previousButtonState[button]);
	}
	
}
