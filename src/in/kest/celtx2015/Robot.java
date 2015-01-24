
package in.kest.celtx2015;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	//Controllers
	private XboxController driverGamepad = new XboxController(0);
	private XboxController operatorGamepad = new XboxController(1);
	
	//Subsystems
	private Drive drive;
	
	//Autonomous
	double autonDelay = 0.0;

	//Called once when the robot boots up.
    public void robotInit() {
		System.out.println("Initializing Robot...");
    	
    	drive = new Drive();

		System.out.println("Done.");
    }
	
    //Called the first time the robot enters disabled mode.
	public void disabledInit(){
		System.out.println("Robot Disabled");
	}
	
	//Called at ~50Hz while the robot is in autonomous.
	public void disabledPeriodic(){
		
	}
	
	//Called each time the robot enters autonomous.
    public void autonomousInit(){
		System.out.println("Autonomous Enabled");
		System.out.printf("Waiting for %.2f seconds.", autonDelay);
		
		Timer delayTimer = new Timer();
		delayTimer.start();
    	
		while(delayTimer.get() < autonDelay);
		
    }

	//Called at ~50Hz while the robot is disabled.
    public void autonomousPeriodic() {

    }

    //Called once each time the robot enters tele-op mode.
    public void teleopInit(){
		System.out.println("Tele-op Enabled");
    	
    }

	//Called at ~50Hz while the robot is enabled.
    public void teleopPeriodic(){
    	//Driver
    	double driveJoyY = Functions.applyJoystickFilter(driverGamepad.getLeftY());
    	double driveJoyX = Functions.applyJoystickFilter(driverGamepad.getLeftX());
    	
    	drive.doArcadeDrive(driveJoyX, driveJoyY);
    	
    	//if button held
    	//	do backup sequence
    	//else
    	//	reset backup sequence
    	
    	//if button pressed
    	// set lift stack flag true
    	//do lift stack
    	
    	//if b
    	
    	
    	//Operator
    	
    	
    	//Other
    	drive.sendDataToSmartDash();
    	driverGamepad.updateButtons();
    	operatorGamepad.updateButtons();
    }
    
}
