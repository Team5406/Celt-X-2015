package ca.team5406.frc2015;

import ca.team5406.util.PID;

public class DrivePID {
	
	private Drive drive;
	
	private PID driveToPID = new PID();
	private PID turnToPID = new PID();
	//Declare DriveTo, TurnTo PIDs

	public DrivePID(Drive drive){
		this.drive = drive;
		resetPidConstants();
	}
	
	public void resetPidConstants(){
		driveToPID.setConstants(Constants.driveToPidKp.getDouble(), 
								Constants.driveToPidKi.getDouble(), 
								Constants.driveToPidKd.getDouble());
		turnToPID.setConstants(Constants.turnToPidKp.getDouble(),
							   Constants.turnToPidKi.getDouble(),
							   Constants.turnToPidKd.getDouble());
	}
	
	public void initDriveToPos(int distance){
		drive.resetEncoders();
		drive.resetGyro();
		driveToPID.initPID(distance, drive.getAverageEncoders());
	}
	
	public boolean driveToPos(){
		double speed = driveToPID.calcSpeed(drive.getAverageEncoders());
		
		double gyroCompensation = drive.getGyroAngle() * Constants.driveToPidGyroFactor.getDouble();
		
		double leftSpeed = speed; // + gyroCompensation;
		double rightSpeed = speed; // - gyroCompensation;
		
		drive.setPowerLeftRight(leftSpeed, rightSpeed);
		
		return driveToPID.isDone(drive.getAverageEncoders(), Constants.driveToPidDeadband.getDouble());
	}
	
	public void initTurnToAngle(double angle){
		drive.resetEncoders();
		drive.resetGyro();
		turnToPID.initPID(angle, drive.getGyroAngle());
	}
	
	public boolean turnToAngle(){
		double speed = turnToPID.calcSpeed(drive.getGyroAngle());
		
		double leftSpeed = speed;
		double rightSpeed = -speed;
		
		drive.setPowerLeftRight(leftSpeed, rightSpeed);
		
		return turnToPID.isDone(drive.getGyroAngle(), Constants.turnToPidDeadband.getDouble());
	}
	
}
