package in.kest.celtx2015;

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
	
	public Drive(){
		leftDriveA = new Talon(0);
		leftDriveB = new Talon(1);
		rightDriveA = new Talon(2);
		rightDriveB = new Talon(3);
		
		leftEncoder = new ReletiveEncoder(0, 1, true);
		rightEncoder = new ReletiveEncoder(2, 3, false);
		gyro = new Gyro(0);
	}
	
	public void doArcadeDrive(double x, double y){
		double leftPower = y + x;
		double rightPower = y - x;
		
		setPowerLeftRight(leftPower, rightPower);
	}
	
	public void doTankDrive(double leftY, double rightY){
		double leftPower = leftY;
		double rightPower = rightY;
		
		setPowerLeftRight(leftY, rightY);
	}
	
	private void setPowerLeftRight(double left, double right){
		leftDriveA.set(left);
		leftDriveB.set(left);
		rightDriveA.set(-right);
		rightDriveB.set(-right);
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
	
	public void sendDataToSmartDash(){
		SmartDashboard.putNumber("Left Drive Enc", getLeftEncoder());
		SmartDashboard.putNumber("Right Drive Enc", getRightEncoder());
		SmartDashboard.putNumber("Gyro Angle", getGyroAngle());
	}
}
