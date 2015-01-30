package ca.team5406.frc2015;

import ca.team5406.util.PID;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

public class Elevator {

	private Victor elevatorMotor;
	private Encoder elevatorEncoder;
	private Solenoid brake;
	
	private PID upPID = new PID();
	private PID downPID = new PID();
	
	public Elevator(){
		elevatorMotor = new Victor(4);
		elevatorEncoder = new Encoder(4, 5);
		brake = new Solenoid(0);
		
		upPID.setCosntants(Constants.elevatorUpPidKp.getDouble(), 
						   Constants.elevatorUpPidKi.getDouble(),
						   Constants.elevatorUpPidKd.getDouble());
		upPID.initPID(getElevatorPosition(), getElevatorPosition());
		
		downPID.setCosntants(Constants.elevatorDownPidKp.getDouble(), 
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
			return upPID.isDone(currentPosition, Constants.elevatorUpPidDeadband.getInt());
		}
		else if(desiredPosition < currentPosition - Constants.elevatorDownPidDeadband.getInt()){
			setBrake(false);
			setElevatorSpeed(downSpeed);
			return downPID.isDone(currentPosition, Constants.elevatorDownPidDeadband.getInt());
		}
		else{
			setElevatorSpeed(0);
			setBrake(true);
			return true;
		}
	}
	
	public void setBrake(boolean shouldBrake){
		brake.set(shouldBrake);
	}
	
	public void setElevatorSpeed(double speed){
		elevatorMotor.set(speed);
	}
	
	public int getElevatorPosition(){
		return elevatorEncoder.get();
	}
	
}
