package ca.team5406.frc2015;

import ca.team5406.util.Functions;
import ca.team5406.util.PID;

public class DrivePID {
	
	private Drive drive;
	
	private PID driveToPID = new PID();
	private PID turnToPID = new PID();
	//Declare DriveTo, TurnTo PIDs
	
	private double speedLimit = 1.0;

	public DrivePID(Drive drive){
		this.drive = drive;
		resetPidConstants();
	}
	
	public void setSpeedLimit(double val){
		speedLimit = val;
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
		setSpeedLimit(1.0);
	}
	
	public boolean driveToPos(){
		double speed = driveToPID.calcSpeed(drive.getAverageEncoders());
		
		speed = Functions.limitValue(speed, speedLimit);
		
		double gyroCompensation = drive.getGyroAngle() * Constants.driveToPidGyroFactor.getDouble();
		
		double leftSpeed = speed; // + gyroCompensation;
		double rightSpeed = speed; // - gyroCompensation;
		
		drive.setPowerLeftRight(leftSpeed, rightSpeed);
		
		if(driveToPID.isDone(drive.getAverageEncoders(), Constants.driveToPidDeadband.getDouble())){
			drive.setPowerLeftRight(0.0, 0.0);
			return true;
		}
		else return false;
	}
	
	public void initTurnToAngle(double angle){
		angle = angle * (122.0 / 90.0);
		drive.resetEncoders();
		drive.resetGyro();
		turnToPID.initPID(angle, drive.getGyroAngle());
		setSpeedLimit(1.0);
	}
	
	public boolean turnToAngle(){
		double speed = turnToPID.calcSpeed(drive.getGyroAngle());

		speed = Functions.limitValue(speed, speedLimit);
		
		double leftSpeed = -speed;
		double rightSpeed = speed;
		
		
		drive.setPowerLeftRight(leftSpeed, rightSpeed);
		
		if(turnToPID.isDone(drive.getGyroAngle(), Constants.turnToPidDeadband.getDouble())){
			drive.setPowerLeftRight(0.0, 0.0);
			return true;
		}
		else return false;
	}
	
}
