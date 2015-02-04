
package ca.team5406.frc2015;

import ca.team5406.frc2015.autonmous.*;
import ca.team5406.util.CameraServer;
import ca.team5406.util.ConstantsBase;
import ca.team5406.util.RegulatedPrinter;
import ca.team5406.util.controllers.AttackStick;
import ca.team5406.util.controllers.XboxController;
import ca.team5406.util.sensors.PressureTransducer;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	//Controllers
	private AttackStick driverGamepad = new AttackStick(0);
	private XboxController operatorGamepad = new XboxController(1);
	
	//Subsystems
	private Drive drive;
	private DrivePID drivePID;
	private PneumaticElevator stacker;
	private Gripper grabber;
	
	private SendableChooser autonSelector;
	private AutonomousRoutine selectedAuto;
	
	private CameraServer cameraServer;
	
	private Compressor compressor = new Compressor();
	private PressureTransducer pressureTransducer = new PressureTransducer(0);
	
	private RegulatedPrinter streamPrinter = new RegulatedPrinter(2.0);
	
	//Autonomous
	double autonDelay = 0.0;

	//Called once when the robot boots up.
    public void robotInit() {
		System.out.println("Initializing Robot...");
		
		ConstantsBase.updateConstantsFromFile();
    	
    	drive = new Drive();
    	drivePID = new DrivePID(drive);
    	stacker = new PneumaticElevator();
    	grabber = new Gripper();
    	compressor.setClosedLoopControl(false);
    	
    	//Default solenoid positions
    	stacker.setElevatorUp(true);
    	stacker.setGripperExpansion(false);
    	
    	try{
	    	//Start sending camera to DS
			cameraServer = CameraServer.getInstance();
			cameraServer.setQuality(50);
			cameraServer.changeCamera("front");;
			cameraServer.startAutomaticCaptureThread();
    	}
    	catch(Exception ex){
    		System.out.println("ERROR: Camera not available");
    	}
		
		//Send autonomous options to DS
		autonSelector = new SendableChooser();
		autonSelector.addDefault("Do Nothing", new DoNothing());
		autonSelector.addObject("Take Ours", new TakeBothOurs(drivePID, stacker));
		autonSelector.addObject("Move to Zone", new MoveToZone(drivePID));
    	
		System.out.println("Done.");
    }
	
    //Called the first time the robot enters disabled mode.
	public void disabledInit(){
		System.out.println("Robot Disabled");
	}
	
	//Called at ~50Hz while the robot is disabled.
	public void disabledPeriodic(){
		
		if(operatorGamepad.getButtonOnce(XboxController.START_BUTTON)){
			ConstantsBase.updateConstantsFromFile();
		}
		
		if(operatorGamepad.getButtonOnce(XboxController.Y_BUTTON)){
			if(autonDelay < 10) autonDelay += 0.1;
		}
		else if(operatorGamepad.getButtonOnce(XboxController.A_BUTTON)){
			if(autonDelay > 0) autonDelay -= 0.1;
		}
		
		sendSmartDashInfo();
		operatorGamepad.updateButtons();
	}
	
	//Called each time the robot enters autonomous.
    public void autonomousInit(){
		System.out.println("Autonomous Enabled");
		System.out.printf("AUTO: Waiting for %.2f seconds.", autonDelay);
		
		Timer delayTimer = new Timer();
		delayTimer.start();
    	
		while(delayTimer.get() < autonDelay){
			SmartDashboard.putNumber("Auton Delay", (autonDelay - delayTimer.get()));
		}
		
		selectedAuto = (AutonomousRoutine) autonSelector.getSelected();
		selectedAuto.routineInit();
		
    }

	//Called at ~50Hz while the robot is in autonomous.
    public void autonomousPeriodic(){
    	if(!selectedAuto.isDone()){
    		selectedAuto.routinePeriodic();
    	}
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
    		drive.setSpeedMultiplier(1.0);
    	}
    	else{
    		drive.setSpeedMultiplier(0.7);
    	}
    	
    	//Move away from stack when button held
    	if(driverGamepad.getButtonHeld(1) && stacker.getElevatorDown()){
        	if(driverGamepad.getButtonOnce(1)){
        		drivePID.initDriveToPos(-1000);
            	stacker.setGripperExpansion(true);
        	}
    		drivePID.driveToPos();
    	}
    	//Otherwise do normal arcade drive
    	else{
        	drive.doArcadeDrive(driverGamepad, 1, 0);
    	}
    	
    	//Operator
    	//Manual compressor control
    	if(compressor.getPressureSwitchValue() || operatorGamepad.getButtonOnce(XboxController.BACK_BUTTON)){
    		compressor.stop();
    	}
    	if(operatorGamepad.getButtonOnce(XboxController.START_BUTTON)){
    		compressor.start();
    	}
    	
    	//Manual Gripper control
    	if(operatorGamepad.getButtonOnce(XboxController.LEFT_BUMPER)){
    		grabber.setGripperExpansion(true);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.RIGHT_BUMPER)){
    		grabber.setGripperExpansion(false);
    	}
    	
    	//Manual Elevator control
    	if(operatorGamepad.getButtonOnce(XboxController.A_BUTTON)){
    		stacker.setElevatorUp(false);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.Y_BUTTON)){
    		stacker.setElevatorUp(true);
    	}
    	//Elevator presets
    	else if(operatorGamepad.getButtonOnce(XboxController.B_BUTTON)){
    		stacker.setElevatorUp(false);
    		stacker.setGripperExpansion(true);
    	}
    	else{
    		stacker.doAutoAddToStack(operatorGamepad.getButtonOnce(XboxController.X_BUTTON));
    	}
    	
    	//Other
    	driverGamepad.updateButtons();
    	operatorGamepad.updateButtons();
    	
    	sendSmartDashInfo();
    	printSensorInfo();
    }
    
    public void sendSmartDashInfo(){
    	SmartDashboard.putNumber("AutonDelay", autonDelay);
    	SmartDashboard.putData("Autonomous", autonSelector);
    	
    	SmartDashboard.putNumber("Pressure", pressureTransducer.getPsi());
    	SmartDashboard.putBoolean("Compressor On", (compressor.getCompressorCurrent() > 0.1));
    	
    	SmartDashboard.putNumber("Heading", (drive.getGyroAngle() % 360));
    }
    
    public void printSensorInfo(){
    	streamPrinter.print("Left Encoder:     " + drive.getLeftEncoder() + "\n" + 
    						"Right Encoder:    " + drive.getRightEncoder() + "\n" +
    						"Gyro:             " + drive.getGyroAngle() + "\n" + 
    						"Elevator Down:    " + stacker.getElevatorDown() + "\n" + 
    						"Auto Stack State: " + stacker.getAutoStackState() + "\n");
    }
    
}
