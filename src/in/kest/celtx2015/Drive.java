package in.kest.celtx2015;

import edu.wpi.first.wpilibj.Talon;

public class Drive {
	private Talon leftDriveA;
	private Talon leftDriveB;
	private Talon rightDriveA;
	private Talon rightDriveB;
	
	public Drive(){
		leftDriveA = new Talon(0);
		leftDriveB = new Talon(1);
		rightDriveA = new Talon(2);
		rightDriveB = new Talon(3);
	}
	
	public void doArcadeDrive(double x, double y){
		double leftPower = y - x;
		double rightPower = y + x;
		
		setPowerLR(x, y);
	}
	
	public void doTankDrive(double leftY, double rightY){
		double leftPower = leftY;
		double rightPower = rightY;
		
		setPowerLR(leftY, rightY);
	}
	
	private void setPowerLR(double left, double right){
		leftDriveA.set(-left);
		leftDriveB.set(-left);
		rightDriveA.set(right);
		rightDriveB.set(right);
	}
}
