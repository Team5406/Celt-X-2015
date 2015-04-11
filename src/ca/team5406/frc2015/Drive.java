package ca.team5406.frc2015;

import ca.team5406.util.Functions;
import ca.team5406.util.controllers.Controller;
import ca.team5406.util.controllers.XboxController;
import ca.team5406.util.sensors.RelativeEncoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Talon;

public class Drive {
	
	private Talon leftDriveA;
	private Talon leftDriveB;
	private Talon rightDriveA;
	private Talon rightDriveB;
	
	private RelativeEncoder leftEncoder;
	private RelativeEncoder rightEncoder;
	
	private Gyro gyro;
	
	private double speedMultiplier = 1.0;
	
	public Drive(){
		leftDriveA = new Talon(Constants.leftDriveTalonA.getInt());
		leftDriveB = new Talon(Constants.leftDriveTalonB.getInt());
		rightDriveA = new Talon(Constants.rightDriveTalonA.getInt());
		rightDriveB = new Talon(Constants.rightDriveTalonB.getInt());
		
		leftEncoder = new RelativeEncoder(Constants.leftDriveEncA.getInt(),
										  Constants.leftDriveEncB.getInt(), false);
		
		rightEncoder = new RelativeEncoder(Constants.rightDriveEncA.getInt(),
										   Constants.rightDriveEncB.getInt(), true);
		
		gyro = new Gyro(Constants.gyro.getInt());
		gyro.initGyro();
	}
	
	public void doArcadeDrive(Controller gamepad, int forwardAxis, int turnAxis, boolean filter){
		double forward = 0;
		double turn = 0;
		if(filter){
			forward = Functions.applyJoystickFilter(-gamepad.getRawAxis(forwardAxis));
			turn = Functions.applyJoystickFilter(gamepad.getRawAxis(turnAxis));
		}
		else{
			forward = (-gamepad.getRawAxis(forwardAxis));
			turn = (gamepad.getRawAxis(turnAxis));
		}
		double leftPower = forward + turn;
		double rightPower = forward - turn;
		
		setPowerLeftRight(leftPower, rightPower);
	}
	
	public void doTankDrive(Controller gamepad, int leftAxis, int rightAxis){
		double leftPower = Functions.applyJoystickFilter(-gamepad.getRawAxis(leftAxis));
		double rightPower = Functions.applyJoystickFilter(-gamepad.getRawAxis(rightAxis));
		
		setPowerLeftRight(leftPower, rightPower);
	}
	
	public void doTriggerDrive(XboxController gamepad, int turnAxis){
		double leftPower = gamepad.getRightTrigger() - gamepad.getLeftTrigger() + gamepad.getRawAxis(turnAxis);
		double rightPower = gamepad.getRightTrigger() + gamepad.getLeftTrigger() + gamepad.getRawAxis(turnAxis);
		
		setPowerLeftRight(leftPower, rightPower);
	}
	
	public void setSpeedMultiplier(double val){
		speedMultiplier = val;
	}
	
	public void setPowerLeftRight(double left, double right){
		leftDriveA.set(left * speedMultiplier);
		leftDriveB.set(left * speedMultiplier);
		rightDriveA.set(-right * speedMultiplier);
		rightDriveB.set(-right * speedMultiplier);
	}
	
	public int getLeftEncoder(){
		return leftEncoder.get();
	}
	
	public int getRightEncoder(){
		return rightEncoder.get();
	}
	
	public int getAverageEncoders(){
		return getLeftEncoder();
//		return (int)((getLeftEncoder() + getRightEncoder()) / 2);
	}
	
	public void resetEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public double getGyroAngle(){
		return gyro.getAngle();
	}
	
	public boolean getRobotMoving(){
		return Math.abs(leftDriveA.get()) > 0.05 && Math.abs(leftDriveB.get()) > 0.05 
			&& Math.abs(rightDriveA.get()) > 0.05 && Math.abs(rightDriveB.get()) > 0.05;
	}
	
	public void resetGyro(){
		gyro.reset();
	}
}

