
package in.kest.celtx2015;

import in.kest.celtx2015.controllers.XboxController;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	//Controllers
	private XboxController driverGamepad = new XboxController(0);
	private XboxController operatorGamepad = new XboxController(1);
	
	//Subsystems
	private Drive drive;
	private Stacker stacker;
	
	private Compressor compressor = new Compressor();
	
	//Autonomous
	double autonDelay = 0.0;

	//Called once when the robot boots up.
    public void robotInit() {
		System.out.println("Initializing Robot...");
    	
    	drive = new Drive();
    	stacker = new Stacker();
    	
    	compressor.setClosedLoopControl(false);
    	
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
		
		if(!compressor.getPressureSwitchValue()){
			compressor.start();
		}
    	
    }

	//Called at ~50Hz while the robot is enabled.
    public void teleopPeriodic(){

    	//Driver 
    	if(driverGamepad.getRawButton(3)){
    		drive.setSpeedMultiplier(0.7);
    	}
    	else{
    		drive.setSpeedMultiplier(1.0);
    	}
    	
    	//Move away from stack when button held
    	if(driverGamepad.getButtonHeld(1)){
        	if(driverGamepad.getButtonOnce(1)){
        		drive.resetDriveTo();
        	}
    		drive.driveToPosition(-1000);
    	}
    	//Otherwise do normal arcade drive
    	else{
        	drive.doArcadeDrive(driverGamepad);
    	}
    	
    	
    	//Operator
    	if(compressor.getPressureSwitchValue() || operatorGamepad.getButtonOnce(operatorGamepad.BACK_BUTTON)){
    		compressor.stop();
    	}
    	if(operatorGamepad.getButtonOnce(XboxController.START_BUTTON)){
    		compressor.start();
    	}
    	
    	if(operatorGamepad.getButtonOnce(1)){
    		stacker.setElevatorUp(false);
    	}
    	else if(operatorGamepad.getButtonOnce(4)){
    		stacker.setElevatorUp(true);
    	}
    	
    	//Other
    	drive.sendDataToSmartDash();
    	driverGamepad.updateButtons();
    	operatorGamepad.updateButtons();
    }
    
}
