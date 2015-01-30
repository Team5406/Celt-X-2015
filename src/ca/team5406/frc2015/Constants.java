package ca.team5406.frc2015;

import ca.team5406.util.ConstantsBase.Constant;

public class Constants {
	
	private Constants(){}
	
	//public static Constant *name* = new Constant("*key*", *defaultValue*);
	
	public static Constant autoAddToStackRaiseDelay = new Constant("raiseStackDelay", 2.0);
	
	//DriveTo PID Constants
	public static Constant driveToPidKp = new Constant("driveToPidKp", 0.0);
	public static Constant driveToPidKi = new Constant("driveToPidKi", 0.0);
	public static Constant driveToPidKd = new Constant("driveToPidKd", 0.0);
	public static Constant driveToPidDeadband = new Constant("driveToPidDeadband", 10);
	public static Constant driveToPidGyroFactor = new Constant("driveToPidGyroFactor", 0.0);
	
	//TurnTo PID Constants
	public static Constant turnToPidKp = new Constant("turnToPidKp", 0.0);
	public static Constant turnToPidKi = new Constant("turnToPidKi", 0.0);
	public static Constant turnToPidKd = new Constant("turnToPidKd", 0.0);
	public static Constant turnToPidDeadband = new Constant("turnToPidDeadband", 0.5);
	
}
