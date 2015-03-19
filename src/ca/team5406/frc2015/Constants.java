package ca.team5406.frc2015;

import ca.team5406.util.ConstantsBase.Constant;

public class Constants {
	
	public Constants(){}
	
	//public static Constant *name* = new Constant("*key*", *defaultValue*);
	
	public static Constant gripperMoveDelay = new Constant("gripperMoveDelay", 0.4);
	
	//DriveTo PID Constants
	public static Constant driveToPidKp = new Constant("driveToPidKp", 0.00033);
	public static Constant driveToPidKi = new Constant("driveToPidKi", 0.002); //0.001
	public static Constant driveToPidKd = new Constant("driveToPidKd", 0.006);
	public static Constant driveToPidDeadband = new Constant("driveToPidDeadband", 100);
	public static Constant driveToPidGyroFactor = new Constant("driveToPidGyroFactor", 0.0);
	
	//TurnTo PID Constants
	public static Constant turnToPidKp = new Constant("turnToPidKp", 0.008);
	public static Constant turnToPidKi = new Constant("turnToPidKi", 0.003);
	public static Constant turnToPidKd = new Constant("turnToPidKd", 0.07);
	public static Constant turnToPidDeadband = new Constant("turnToPidDeadband", 1.0);
	
	//Elevator Up PID Constants
	public static Constant elevatorUpPidKp = new Constant("elevatorUpPidKp", 0.003); //0.0014
	public static Constant elevatorUpPidKi = new Constant("elevatorUpPidKi", 0.01); //0.0035
	public static Constant elevatorUpPidKd = new Constant("elevatorUpPidKd", 0.0033);
	public static Constant elevatorUpPidDeadband = new Constant("elevatorUpPidDeadband", 300);
	
	//Elevator Down PID Constants
	public static Constant elevatorDownPidKp = new Constant("elevatorDownPidKp", 0.00013);
	public static Constant elevatorDownPidKi = new Constant("elevatorDownPidKi", 0.0005);
	public static Constant elevatorDownPidKd = new Constant("elevatorDownPidKd", 0.0052);
	public static Constant elevatorDownPidKiAlt = new Constant("elevatorDownPidKiAlt", 0.12);
	public static Constant elevatorDownPidDeadband = new Constant("elevatorDownPidDeadband", 300);
	
	//Elevator Position Presets
	public static Constant elevatorFloorPreset = new Constant("elevatorFloorPreset", -100);
	public static Constant elevatorUpPreset = new Constant("elevatorUpPreset", 10100);
	public static Constant elevatorCarryPreset = new Constant("elevatorCarryPreset", 2500);
	public static Constant elevatorOneUpPreset = new Constant("elevatorOneUpPreset", 3100);

	//Speeds
	public static Constant rollerSpeed = new Constant("rollerSpeed", -0.2);
	public static Constant highDriveSpeedMutlipler = new Constant("highDriveSpeedMutlipler", 1.0);
	public static Constant midDriveSpeedMutlipler = new Constant("midDriveSpeedMutlipler", 0.7);
	public static Constant lowDriveSpeedMutlipler = new Constant("lowDriveSpeedMutlipler", 0.5);
	
	//Ports
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
	public static Constant gyro = new Constant("gyro", 1);
	public static Constant pressureTransducer = new Constant("pressureTransducer", 0);
	
	//Solenoid
	public static Constant elevatorUnbrake = new Constant("elevatorUnbrake", 0);
	public static Constant elevatorBrake = new Constant("elevatorBrake", 1);
	public static Constant gripperContraction = new Constant("gripperContraction", 3);
	public static Constant gripperExpansion = new Constant("gripperExpansion", 2);
	
}
