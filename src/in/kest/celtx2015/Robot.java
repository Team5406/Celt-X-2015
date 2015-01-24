
package in.kest.celtx2015;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	
	private XboxController driverGamepad;
	
	private Drive drive;

	//Called once when the robot boots up.
    public void robotInit() {
    	driverGamepad = new XboxController(0);
    	
    	drive = new Drive();
    }
	
    //Called the first time the robot enters disabled mode.
	public void disabledInit(){
		
	}
	
	//Called at ~50Hz while the robot is in autonomous.
	public void disabledPeriodic(){
		
	}
	
	//Called each time the robot enters autonomous.
    public void autonomousInit(){
    	
    }

	//Called at ~50Hz while the robot is disabled.
    public void autonomousPeriodic() {

    }

    //Called once each time the robot enters teleop mode.
    public void teleopInit(){
    	
    }

	//Called at ~50Hz while the robot is enabled.
    public void teleopPeriodic(){
    	double driveJoyY = Functions.applyJoystickFilter(driverGamepad.getLeftY());
    	double driveJoyX = Functions.applyJoystickFilter(driverGamepad.getLeftX());
    	
    	drive.doArcadeDrive(driveJoyY, driveJoyX);
    	
    	drive.sendDataToSmartDash();
    }
    
}
