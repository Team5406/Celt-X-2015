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
		leftDriveA = new Talon(0);
		leftDriveB = new Talon(1);
		rightDriveA = new Talon(2);
		rightDriveB = new Talon(3);
		
		leftEncoder = new RelativeEncoder(0, 1, true);
		rightEncoder = new RelativeEncoder(2, 3, false);
		
		gyro = new Gyro(0);
		gyro.initGyro();
	}
	
	public void doArcadeDrive(Controller gamepad, int forwardAxis, int turnAxis){
		double forward = Functions.applyJoystickFilter(-gamepad.getRawAxis(forwardAxis));
		double turn = Functions.applyJoystickFilter(gamepad.getRawAxis(turnAxis));
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
		return (int)((getLeftEncoder() + getRightEncoder()) / 2);
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
