
package ca.team5406.frc2015;

import ca.team5406.util.controllers.AttackStick;
import ca.team5406.util.controllers.XboxController;
import ca.team5496.util.ConstantsBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	//Controllers
	private AttackStick driverGamepad = new AttackStick(0);
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
		
		ConstantsBase.updateConstantsFromFile();
    	
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
		if(driverGamepad.getButtonOnce(1)){
			ConstantsBase.updateConstantsFromFile();
		}
		
		if(operatorGamepad.getButtonOnce(XboxController.Y_BUTTON)){
			if(autonDelay < 10) autonDelay += 0.1;
		}
		else if(operatorGamepad.getButtonOnce(XboxController.A_BUTTON)){
			if(autonDelay > 0) autonDelay -= 0.1;
		}
		
		SmartDashboard.putNumber("Auton Delay", autonDelay);
	}
	
	//Called each time the robot enters autonomous.
    public void autonomousInit(){
		System.out.println("Autonomous Enabled");
		System.out.printf("Waiting for %.2f seconds.", autonDelay);
		
		Timer delayTimer = new Timer();
		delayTimer.start();
    	
		while(delayTimer.get() < autonDelay){
			SmartDashboard.putNumber("Auton Delay", (autonDelay - delayTimer.get()));
		}
		
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
    	if(compressor.getPressureSwitchValue() || operatorGamepad.getButtonOnce(XboxController.BACK_BUTTON)){
    		compressor.stop();
    	}
    	if(operatorGamepad.getButtonOnce(XboxController.START_BUTTON)){
    		compressor.start();
    	}
    	
    	if(operatorGamepad.getButtonOnce(XboxController.A_BUTTON)){
    		stacker.setElevatorUp(false);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.Y_BUTTON)){
    		stacker.setElevatorUp(true);
    	}
    	
    	//Other
    	drive.sendDataToSmartDash();
    	driverGamepad.updateButtons();
    	operatorGamepad.updateButtons();
    }
    
}
