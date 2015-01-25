package in.kest.celtx2015;

import in.kest.celtx2015.controlers.XboxController;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	
	public void doArcadeDrive(XboxController gamepad){
		double forward = Functions.applyJoystickFilter(gamepad.getLeftY());
		double turn = Functions.applyJoystickFilter(gamepad.getLeftX());
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
	
	private void setPowerLeftRight(double left, double right){
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
	
	public void resetDriveTo(){
		
	}
	
	public boolean driveToPosition(int position){
		return false;
	}
	
	public void resetTurnTo(){
		
	}
	
	public boolean turnToAngle(double angle){
		return false;
	}
	
	public void sendDataToSmartDash(){
		SmartDashboard.putNumber("Left Drive Enc", getLeftEncoder());
		SmartDashboard.putNumber("Right Drive Enc", getRightEncoder());
		SmartDashboard.putNumber("Gyro Angle", getGyroAngle());
	}
}
