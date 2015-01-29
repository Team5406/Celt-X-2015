package ca.team5406.frc2015;

import ca.team5406.util.Functions;
import ca.team5406.util.controllers.XboxController;
import ca.team5406.util.sensors.ReletiveEncoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Drive {
	private Talon leftDriveA;
	private Talon leftDriveB;
	private Talon rightDriveA;
	private Talon rightDriveB;
	
	private ReletiveEncoder leftEncoder;
	private ReletiveEncoder rightEncoder;
	
	private Gyro gyro;
	
	private double speedMultiplier = 1.0;
	
	public Drive(){
		leftDriveA = new Talon(0);
		leftDriveB = new Talon(1);
		rightDriveA = new Talon(2);
		rightDriveB = new Talon(3);
		
		leftEncoder = new ReletiveEncoder(0, 1, true);
		rightEncoder = new ReletiveEncoder(2, 3, false);
		
		gyro = new Gyro(0);
	}
	public void doArcadeDrive(Joystick gamepad){
		double forward = Functions.applyJoystickFilter(-gamepad.getRawAxis(1));
		double turn = Functions.applyJoystickFilter(gamepad.getRawAxis(0));
		double leftPower = forward + turn;
		double rightPower = forward - turn;
		
		setPowerLeftRight(leftPower, rightPower);
	}
	
	public void doTankDrive(XboxController gamepad){
		double leftPower = Functions.applyJoystickFilter(gamepad.getLeftY());
		double rightPower = Functions.applyJoystickFilter(gamepad.getRightY());
		
		setPowerLeftRight(leftPower, rightPower);
	}
	
	public void doTriggerDrive(XboxController gamepad){
		double leftPower = gamepad.getRightTrigger() - gamepad.getLeftTrigger() + gamepad.getLeftX();
		double rightPower = gamepad.getRightTrigger() + gamepad.getLeftTrigger() + gamepad.getLeftX();
		
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
	
	public void resetEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public double getGyroAngle(){
		return gyro.getAngle();
	}
	
	public void resetGyro(){
		gyro.reset();
	}
}
