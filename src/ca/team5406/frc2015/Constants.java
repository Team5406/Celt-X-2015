package ca.team5406.frc2015;

import ca.team5406.util.ConstantsBase.Constant;

public class Constants {
	
	public Constants(){}
	
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
	
	//Elevator Up PID Constants
	public static Constant elevatorUpPidKp = new Constant("elevatorUpPidKp", 0.0);
	public static Constant elevatorUpPidKi = new Constant("elevatorUpPidKi", 0.0);
	public static Constant elevatorUpPidKd = new Constant("elevatorUpPidKd", 0.0);
	public static Constant elevatorUpPidDeadband = new Constant("elevatorUpPidDeadband", 0.5);
	
	//Elevator Down PID Constants
	public static Constant elevatorDownPidKp = new Constant("elevatorDownPidKp", 0.0);
	public static Constant elevatorDownPidKi = new Constant("elevatorDownPidKi", 0.0);
	public static Constant elevatorDownPidKd = new Constant("elevatorDownPidKd", 0.0);
	public static Constant elevatorDownPidDeadband = new Constant("elevatorDownPidDeadband", 0.5);
	
	//Elevator Position Presets
	public static Constant elevatorFloorPreset = new Constant("elevatorFloorPreset", 0);
	public static Constant elevatorUpPreset = new Constant("elevatorUpPreset", 0);
	public static Constant elevatorCarryPreset = new Constant("elevatorCarryPreset", 0);
	
}
