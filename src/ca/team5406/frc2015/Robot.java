
package ca.team5406.frc2015;

import ca.team5406.frc2015.autonmous.*;
import ca.team5406.util.CameraServer;
import ca.team5406.util.ConstantsBase;
import ca.team5406.util.Functions;
import ca.team5406.util.RegulatedPrinter;
import ca.team5406.util.controllers.LogitechGamepad;
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
	
	private CameraServer cameraServer;
	
	private Compressor compressor = new Compressor();
	private PressureTransducer pressureTransducer;
	
	private RegulatedPrinter riologPrinter = new RegulatedPrinter(2.0);
	
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
    	compressor.setClosedLoopControl(false);
    	
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
		autonSelector.addObject("Move to Zone", new MoveToZone(drivePID));
    	
		System.out.println("Done.");
    }
	
    //Called the first time the robot enters disabled mode.
	public void disabledInit(){
		System.out.println("Robot Disabled");
	}
	
	//Called at ~50Hz while the robot is disabled.
	public void disabledPeriodic(){
		
		if(operatorGamepad.getButtonOnce(LogitechGamepad.Button3)){
			ConstantsBase.updateConstantsFromFile();
			drivePID.resetPidConstants();
			elevator.resetPidConstants();
			elevator.resetI();
		}
		
		if(operatorGamepad.getButtonOnce(LogitechGamepad.Button4)){
			if(autonDelay < 10) autonDelay += 0.1;
		}
		else if(operatorGamepad.getButtonOnce(LogitechGamepad.Button5)){
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
    }

	//Called at ~50Hz while the robot is enabled.
    public void teleopPeriodic(){

    	//Driver 
    	if(driverGamepad.getButtonHeld(XboxController.X_BUTTON)){
    		drive.setSpeedMultiplier(1.0);
    	}
    	else if(driverGamepad.getButtonHeld(XboxController.B_BUTTON)){
    		drive.setSpeedMultiplier(0.5);
    	}
    	else{
    		drive.setSpeedMultiplier(0.7);
    	}
    	
    	//Change Cameras
    	if(driverGamepad.getDirectionPad() == 0){
    		//cameraServer.changeCamera("front");
    	}
    	else if(driverGamepad.getDirectionPad() == 180){
    		//cameraServer.changeCamera("rear");
    	}
    	
    	//TODO: Add backup from stack button.
    	drive.doArcadeDrive(driverGamepad, 1, 0);
    	
    	//Operator
    	//Manual compressor control
    	if(compressor.getPressureSwitchValue() || operatorGamepad.getButtonOnce(XboxController.BACK_BUTTON)){
    		compressor.stop();
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.START_BUTTON)){
    		compressor.start();
    	}
    	
    	//Manual Gripper control
    	if(operatorGamepad.getButtonOnce(XboxController.RIGHT_BUMPER)){
    		gripper.setGripperExpansion(true);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.LEFT_BUMPER)){
    		gripper.setGripperExpansion(false);
    	}
    	
    	//TODO: Add more/refine stacker control buttons.
//    	if(operatorGamepad.getButtonOnce(XboxController.X_BUTTON)){
//    		stacker.addToStack();
//    	}
    	if(operatorGamepad.getButtonOnce(XboxController.B_BUTTON)){
    		stacker.setDesiredPostition(Stacker.StackerPositions.carryClosed);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.A_BUTTON)){
    		stacker.setDesiredPostition(Stacker.StackerPositions.floorClosed);
    	}
    	else if(operatorGamepad.getButtonOnce(XboxController.Y_BUTTON)){
    		stacker.setDesiredPostition(Stacker.StackerPositions.upClosed);
    	}
    	
    	if(Math.abs(operatorGamepad.getLeftY()) > 0.15 && operatorGamepad.getButtonHeld(XboxController.X_BUTTON)){
    		elevator.setBrake(false);
    		elevator.setElevatorSpeed(Functions.applyJoystickFilter(operatorGamepad.getLeftY()) * (operatorGamepad.getButtonHeld(1) ? 0.6 : 1.0));
    	}
    	else{
    		elevator.setBrake(true);
    		elevator.setElevatorSpeed(0.0);
    	}
    	
    	switch(operatorGamepad.getDirectionPad()){
    	case 180:
    		elevator.setBrake(true);
    		break;
    	case 270:
    		elevator.setBrake(false);
    		break;    	
    	}
//    	//TODO: Brake Control
//    	if(operatorGamepad.getButtonOnce(LogitechGamepad.Button9)){
//    		elevator.setBrake(true);
//    	}
//    	else if(operatorGamepad.getButtonOnce(LogitechGamepad.Button10)){
//    		elevator.setBrake(false);
//    	}
    	
    	//TODO: TEMP: Encoder resets
    	if(operatorGamepad.getButtonOnce(XboxController.LEFT_STICK)){
    		elevator.resetEncoder();
    	}    	
    	
    	//TODO: TEMP: toteroller code
    	toteRoller.setSpeed(operatorGamepad.getLeftTrigger());	
    	
    	//Other
    	//stacker.doAutoLoop(); //Uncomment when the PID is done
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
    	riologPrinter.print("Left Encoder:     " + drive.getLeftEncoder() + "\n" + 
    						"Right Encoder:    " + drive.getRightEncoder() + "\n" +
    						"Elevator Encoder: " + elevator.getElevatorPosition() + "\n" +
    						"Gyro:             " + drive.getGyroAngle() + "\n" +
    						"DPad" + operatorGamepad.getDirectionPad());
    }
    
}
