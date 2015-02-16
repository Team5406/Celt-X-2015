package ca.team5406.frc2015;

import ca.team5406.util.PID;
import ca.team5406.util.sensors.RelativeEncoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Elevator {

	private Victor elevatorMotorA;
	private Victor elevatorMotorB;
	private RelativeEncoder elevatorEncoder;
	private Solenoid brake;
	private Solenoid unbrake;
	
	private PID upPID = new PID();
	private PID downPID = new PID();
	
	public Elevator(){
		elevatorMotorA = new Victor(Constants.elevatorVictorA.getInt());
		elevatorMotorB = new Victor(Constants.elevatorVictorB.getInt());
		elevatorEncoder = new RelativeEncoder(Constants.elevatorEncA.getInt(),
											  Constants.elevatorEncB.getInt());
		brake = new Solenoid(1);
		unbrake = new Solenoid(0);
		
		upPID.setConstants(Constants.elevatorUpPidKp.getDouble(),
						   Constants.elevatorUpPidKi.getDouble(),
						   Constants.elevatorUpPidKd.getDouble());
		upPID.initPID(getElevatorPosition(), getElevatorPosition());
		
		downPID.setConstants(Constants.elevatorDownPidKp.getDouble(), 
						   Constants.elevatorDownPidKi.getDouble(),
						   Constants.elevatorDownPidKd.getDouble());
		downPID.initPID(getElevatorPosition(), getElevatorPosition());
	}
	
	public boolean setElevatorPosition(int desiredPosition){
		int currentPosition = getElevatorPosition();

		upPID.setDesiredPosition(desiredPosition);
		downPID.setDesiredPosition(desiredPosition);
		
		double upSpeed = upPID.calcSpeed(currentPosition);
		double downSpeed = downPID.calcSpeed(currentPosition);
		
		if(desiredPosition > currentPosition + Constants.elevatorUpPidDeadband.getInt()){
			setBrake(false);
			setElevatorSpeed(upSpeed);
			
			if(upSpeed > 0.6) upSpeed = 0.6;
			
			return upPID.isDone(currentPosition, Constants.elevatorUpPidDeadband.getInt());
		}
		else if(desiredPosition < currentPosition - Constants.elevatorDownPidDeadband.getInt()){
			setBrake(false);
			
			if((currentPosition < 2000) && (desiredPosition == Constants.elevatorFloorPreset.getInt())){
				downPID.setConstants(Constants.elevatorDownPidKp.getDouble(), 
						   			 Constants.elevatorDownPidKi.getDouble() + 0.015,
						   			 Constants.elevatorDownPidKd.getDouble());
			}
			else{
				downPID.setConstants(Constants.elevatorDownPidKp.getDouble(), 
			   			 Constants.elevatorDownPidKi.getDouble(),
			   			 Constants.elevatorDownPidKd.getDouble());
				if(downSpeed < -0.3) downSpeed = -0.3;
			}
			
			setElevatorSpeed(downSpeed);
			return downPID.isDone(currentPosition, Constants.elevatorDownPidDeadband.getInt());
		}
		else{
			setElevatorSpeed(0.0);
			setBrake(true);
			return true;
		}
	}
	
	public void resetI(){
		downPID.resetAccumI();
	}
	
	public void setBrake(boolean shouldBrake){
		unbrake.set(!shouldBrake);
		brake.set(shouldBrake);	
	
	}
	public void setElevatorSpeed(double speed){
		elevatorMotorA.set(speed);
		elevatorMotorB.set(speed);
	}
	
	public int getElevatorPosition(){
		return elevatorEncoder.get();
	}
	public void resetEncoder(){
		elevatorEncoder.reset();
	}
	
	public void resetPidConstants(){
		downPID.setConstants(Constants.elevatorDownPidKp.getDouble(), 
				   			 Constants.elevatorDownPidKi.getDouble(),
				   			 Constants.elevatorDownPidKd.getDouble());

		upPID.setConstants(Constants.elevatorUpPidKp.getDouble(), 
						   Constants.elevatorUpPidKi.getDouble(),
						   Constants.elevatorUpPidKd.getDouble());
	}
	
	
}
	

