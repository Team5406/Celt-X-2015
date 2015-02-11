package ca.team5406.frc2015;

import ca.team5406.util.ConstantsBase.Constant;

public class Constants {
	
	public Constants(){}
	
	//public static Constant *name* = new Constant("*key*", *defaultValue*);
	
	public static Constant gripperMoveDelay = new Constant("gripperMoveDelay", 0.4);
	
	//DriveTo PID Constants
	public static Constant driveToPidKp = new Constant("driveToPidKp", 0.00035);
	public static Constant driveToPidKi = new Constant("driveToPidKi", 0.001);
	public static Constant driveToPidKd = new Constant("driveToPidKd", 0.006);
	public static Constant driveToPidDeadband = new Constant("driveToPidDeadband", 100);
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
	public static Constant elevatorFloorPreset = new Constant("elevatorFloorPreset", 10);
	public static Constant elevatorUpPreset = new Constant("elevatorUpPreset", 10000);
	public static Constant elevatorCarryPreset = new Constant("elevatorCarryPreset", 1500);
	
	//s
	//Controllers
	public static Constant driverGamepad = new Constant("driverGamepad", 0);
	public static Constant operatorGamepad = new Constant("operatorGamepad", 1);
	
	//PWM
	public static Constant leftDriveTalonA = new Constant("leftDriveTalonA", 0);
	public static Constant leftDriveTalonB = new Constant("leftDriveTalonB", 1);
	public static Constant rightDriveTalonA = new Constant("rightDriveTalonA", 2);
	public static Constant rightDriveTalonB = new Constant("rightDriveTalonB", 3);
	public static Constant elevatorVictorA = new Constant("elevatorVictorA", 4);
	public static Constant elevatorVictorB = new Constant("elevatorVictorB", 5);
	public static Constant toteStopperVictor = new Constant("toteStopperVictor", 6);
	
	//Digital
	public static Constant leftDriveEncA = new Constant("leftDriveEncA", 0);
	public static Constant leftDriveEncB = new Constant("leftDriveEncB", 1);
	public static Constant rightDriveEncA = new Constant("rightDriveEncA", 2);
	public static Constant rightDriveEncB = new Constant("rightDriveEncB", 3);
	public static Constant elevatorEncA = new Constant("elevatorEncA", 4);
	public static Constant elevatorEncB = new Constant("elevatorEncB", 5);
	
	//Analog
	public static Constant gyro = new Constant("gyro", 0);
	public static Constant pressureTransducer = new Constant("pressureTransducer", 1);
	
	//Solenoid
	public static Constant elevatorBrake = new Constant("elevatorBrake", 0);
	public static Constant gripperContraction = new Constant("gripperContraction", 2);
	public static Constant gripperExpansion = new Constant("gripperExpansion", 3);
}
