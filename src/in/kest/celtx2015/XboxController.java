package in.kest.celtx2015;

import edu.wpi.first.wpilibj.Joystick;

public class XboxController extends Joystick {
	
	int numButtons = 10;
	boolean[] previousButtonState = new boolean[numButtons];

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	public static final int X_BUTTON = 3;
	public static final int Y_BUTTON = 4;
	public static final int LEFT_STICK = 5;
	public static final int RIGHT_STICK = 6;
	public static final int LEFT_BUMPER = 7;
	public static final int RIGHT_BUMPER = 8;
	public static final int BACK_BUTTON = 9;
	public static final int START_BUTTON = 10;

	public XboxController(int port) {
		super(port);
		updateButtons();
	}
	
	public void updateButtons(){
		for(int i = 1; i < numButtons; i++){
			previousButtonState[i] = getRawButton(i);
		}
	}
	
	public boolean getRawButton(int button){
		return super.getRawButton(button);
	}
	
	public boolean getButtonOnce(int button){
		return (super.getRawButton(button) && !previousButtonState[button]);
	}
	
	public boolean getButtonRelease(int button){
		return (!super.getRawButton(button) && previousButtonState[button]);
	}
	
	public double getRawAxis(int axis){
		return super.getRawAxis(axis);
	}
	
	public double getLeftX(){
		return super.getRawAxis(0);
	}
	
	public double getLeftY(){
		return -super.getRawAxis(1);
	}
	
	public double getRightX(){
		return super.getRawAxis(4);
	}
	
	public double getRightY(){
		return -super.getRawAxis(5);
	}
	
	public double getLeftTrigger(){
		return super.getRawAxis(2);
	}
	
	public double getRightTrigger(){
		return super.getRawAxis(3);
	}

}
