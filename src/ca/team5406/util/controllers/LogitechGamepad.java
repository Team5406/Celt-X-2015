package ca.team5406.util.controllers;

public class LogitechGamepad extends Controller{
	
	public static final int Button1 = 1;
	public static final int Button2 = 2;
	public static final int Button3 = 3;
	public static final int Button4 = 4;
	public static final int Button5 = 5;
	public static final int Button6 = 6;
	public static final int Button7 = 7;
	public static final int Button8 = 8;
	public static final int Button9 = 9;
	public static final int Button10 = 10;
	public static final int Button11 = 11;
	public static final int Button12 = 12;
	
	public LogitechGamepad(int port) {
		super(port);
		super.updateButtons();
	}
	public double getXAxis(){
		return super.getRawAxis(0);
	}
	public double getYAxis(){
		return -super.getRawAxis(1);
	}
	public double getZAxis(){
		return super.getRawAxis(2);
	}
	public double getSlider(){
		return -super.getRawAxis(3);
	}
	public int getDirectionPad(){
		return super.getPOV();
	}
}
