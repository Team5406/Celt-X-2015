package ca.team5406.util.controllers;

public class XboxController extends Controller {
	

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	public static final int X_BUTTON = 3;
	public static final int Y_BUTTON = 4;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	public static final int BACK_BUTTON = 7;
	public static final int START_BUTTON = 8;
	public static final int LEFT_STICK = 9;
	public static final int RIGHT_STICK = 10;

	public XboxController(int port) {
		super(port);
		super.updateButtons();
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
	
	public int getDirectionPad(){
		return super.getPOV();
	}

}
