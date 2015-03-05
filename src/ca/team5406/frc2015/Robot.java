
package ca.team5406.frc2015;

import ca.team5406.frc2015.autonmous.*;
import ca.team5406.util.ConstantsBase;
import ca.team5406.util.Functions;
import ca.team5406.util.MultiCamServer;
import ca.team5406.util.RegulatedPrinter;
import ca.team5406.util.controllers.XboxController;
import ca.team5406.util.sensors.PressureTransducer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	//Controllers
	private XboxController driverGamepad = new XboxController(Constants.driverGamepad.getInt());
	private XboxController operatorGamepad = new XboxController(Constants.operatorGamepad.getInt());
	
	//Subsystems
	private Drive drive;
	private DrivePID drivePID;
	private Gripper gripper;
	private Elevator elevator;
	private Stacker stacker;

	private ToteRoller toteRoller;
	
	private SendableChooser autonSelector;
	private AutonomousRoutine selectedAuto;
	
	private MultiCamServer cameraServer;
	//private LightController lightController;
	
	private Compressor compressor = new Compressor();
	private PressureTransducer pressureTransducer;
	
	private RegulatedPrinter riologPrinter = new RegulatedPrinter(0.5);
	
	//Autonomous
	double autonDelay = 0.0;

	//Called once when the robot boots up.
    public void robotInit() {
		System.out.println("Initializing Robot...");
		
		new Constants();
		ConstantsBase.updateConstantsFromFile();
    	
    	drive = new Drive();
    	drivePID = new DrivePID(drive);
    	gripper = new Gripper();
    	elevator = new Elevator();
    	stacker = new Stacker(elevator, gripper);
    	toteRoller = new ToteRoller();
    	
    	pressureTransducer = new PressureTransducer(Constants.pressureTransducer.getInt());
    	gripper.setGripperExpansion(false);
    	compressor.setClosedLoopControl(true);
    	
    	//lightController = new LightController();
    	
    	try{
	    	//Start sending camera to DS
//			cameraServer = new MultiCamServer();
    	}
    	catch(Exception ex){
    		System.out.println("ERROR: Cameras not available");
    	}
		
		//Send autonomous options to DS
		autonSelector = new SendableChooser();
		autonSelector.addDefault("Do nothing", new DoNothing());
		autonSelector.addObject("Move to zone", new MoveToZone(drivePID));
//		autonSelector.addObject("Set up for noodle", new SetUpNoodling(drivePID, stacker));
//		autonSelector.addObject("Take our can", new TakeOurCan(drivePID, stacker));
//		autonSelector.addObject("Take our tote", new TakeOurTote(drivePID, stacker));
//		autonSelector.addObject("Take our tote and can", new TakeOurToteAndCan(drivePID, stacker));
    	
		System.out.println("Done.");
    }
	
    //Called the first time the robot enters disabled mode.
	public void disabledInit(){
		System.out.println("Robot Disabled");

//		if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
//			lightController.setUnderglowColor(0.0, 0.0, 1.0);
//		}
//		if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red){
//			lightController.setUnderglowColor(1.0, 0.0, 0.0);
//		}
//		else{
//			lightController.setUnderglowColor(0.0, 1.0, 0.0);
//		}
//		lightController.setLightPattern(LightController.PixelLightPatterns.rainbow);
//		lightController.updateLights();
	}
	
	//Called at ~50Hz while the robot is disabled.
	public void disabledPeriodic(){

    	//Change Cameras
    	if(driverGamepad.getButtonOnce(XboxController.A_BUTTON)){
    		try{ cameraServer.setCamera("front"); }
    		catch(Exception ex){ System.out.println("ERROR: Cannot change cameras."); }
    	}
    	else if(driverGamepad.getButtonOnce(XboxController.Y_BUTTON)){
    		try{ cameraServer.setCamera("rear"); }
    		catch(Exception ex){ System.out.println("ERROR: Cannot change cameras."); }
    	}
		
		if(operatorGamepad.getButtonOnce(XboxController.X_BUTTON)){
			ConstantsBase.updateConstantsFromFile();
			drivePID.resetPidConstants();
			elevator.resetPidConstants();
			elevator.resetI();
		}
		
		if(operatorGamepad.getButtonOnce(XboxController.Y_BUTTON)){
			if(autonDelay < 10.0) autonDelay += 0.1;
		}
		else if(operatorGamepad.getButtonOnce(XboxController.A_BUTTON)){
			if(autonDelay > 0.0) autonDelay -= 0.1;
		}
		
		sendSmartDashInfo();
		operatorGamepad.updateButtons();
//		cameraServer.sendImage();
	}
	
	//Called each time the robot enters autonomous.
    public void autonomousInit(){
		System.out.println("Autonomous Enabled");
		System.out.printf("AUTO: Waiting for %.2f seconds.", autonDelay);
		
		Timer delayTimer = new Timer();
		delayTimer.start();
    	
		//Wait the specified amount of time
		while(autonDelay > 0.0 && delayTimer.get() < autonDelay){
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
    }

	//Called at ~50Hz while the robot is enabled.
    public void teleopPeriodic(){

    	//Driver 
    	//Speed Scaling
    	if(driverGamepad.getButtonHeld(XboxController.X_BUTTON)){
    		drive.setSpeedMultiplier(Constants.highDriveSpeedMutlipler.getDouble());
    	}
    	else if(driverGamepad.getButtonHeld(XboxController.B_BUTTON)){
    		drive.setSpeedMultiplier(Constants.midDriveSpeedMutlipler.getDouble());
    	}
    	else{
    		drive.setSpeedMultiplier(Constants.lowDriveSpeedMutlipler.getDouble());
    	}
    	
    	//Change Cameras
    	try{
	    	if(driverGamepad.getButtonOnce(XboxController.A_BUTTON)){
	    			cameraServer.setCamera("front");
	    	}
	    	else if(driverGamepad.getButtonOnce(XboxController.Y_BUTTON)){
	    			cameraServer.setCamera("rear");
	    	}
    	}
    	catch(Exception ex){
			System.out.println("ERROR: Cannot change cameras.");
    	}
    	
    	//TODO: Add backup from stack button.
    	drive.doArcadeDrive(driverGamepad, 1, 0);
    	
    	//Operator
    	//Manual Gripper control
    	if(operatorGamepad.getButtonOnce(XboxController.RIGHT_BUMPER)){
    		gripper.setGripperExpansion(true);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.LEFT_BUMPER)){
    		gripper.setGripperExpansion(false);
    	}
    	
    	//Elevator Postions
    	if(operatorGamepad.getButtonOnce(XboxController.X_BUTTON)){
    		stacker.addToStack();
    	}
    	if(operatorGamepad.getButtonOnce(XboxController.B_BUTTON)){
    		stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.A_BUTTON)){
    		stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.Y_BUTTON)){
    		stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.START_BUTTON)){
    		stacker.setDesiredPostition(Stacker.StackerPositions.manualControl);
    	}
    	
    	//Auto or Manual elevator control
    	if(stacker.getDesiredPosition() == Stacker.StackerPositions.manualControl){
    		if(Math.abs(operatorGamepad.getLeftY()) >= 0.1){
    			elevator.setBrake(false);
    			elevator.setElevatorSpeed(Functions.applyJoystickFilter(operatorGamepad.getLeftY()));
    		}
    		else{
    			elevator.setBrake(true);
    		}
    	}
    	else{
    		stacker.doAutoLoop();
    	}
    	
    	//Manual Elevator Encoder Reset
    	if(operatorGamepad.getButtonOnce(XboxController.RIGHT_STICK) && operatorGamepad.getButtonOnce(XboxController.LEFT_STICK)){
    		elevator.resetEncoder();
    	}    	
    	
    	//Tote Roller
    	if(operatorGamepad.getButtonOnce(XboxController.BACK_BUTTON)){
    		toteRoller.toggleState();
    	}
    	
    	//Other
    	driverGamepad.updateButtons();
    	operatorGamepad.updateButtons();
    	toteRoller.doToteRoller();
//    	lightController.updateLights();
//    	cameraServer.sendImage();
    	sendSmartDashInfo();
    	printSensorInfo();
    }
    
    public void sendSmartDashInfo(){
    	SmartDashboard.putNumber("AutonDelay", autonDelay);
    	SmartDashboard.putData("Autonomous", autonSelector);
    	
//    	SmartDashboard.putNumber("Elevator", elevator.getElevatorPosition());
    	SmartDashboard.putBoolean("Tote Toller", toteRoller.getStatus());
    	SmartDashboard.putBoolean("Compressor On", (compressor.getCompressorCurrent() > 0.0));
    	SmartDashboard.putNumber("Heading", (((drive.getGyroAngle() % 360) + 360) % 360));
    	
    	SmartDashboard.putNumber("Pressure", pressureTransducer.getPsi());
    }
    
    public void printSensorInfo(){
    	riologPrinter.print("Left Encoder:     " + drive.getLeftEncoder() + "\n" + 
    						"Right Encoder:    " + drive.getRightEncoder() + "\n" +
//    						"Elevator Encoder: " + elevator.getElevatorPosition() + "\n" +
    						"Gyro:             " + drive.getGyroAngle() + "\n");
    }
    
}
