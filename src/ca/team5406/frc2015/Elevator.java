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
		brake = new Solenoid(Constants.elevatorBrake.getInt());
		unbrake = new Solenoid(Constants.elevatorUnbrake.getInt());
		
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
		
		if(desiredPosition > currentPosition){
			setBrake(false);
			setElevatorSpeed(upSpeed);
			
			if(upSpeed > 0.7) upSpeed = 0.7;
			
			if(upPID.isDone(currentPosition, Constants.elevatorUpPidDeadband.getInt())){
				setBrake(true);
				return true;
			}
			else return false;
		}
		else if(desiredPosition < currentPosition){
			setBrake(false);
			
			if((currentPosition < Constants.elevatorOneUpPreset.getInt()) && (desiredPosition == Constants.elevatorFloorPreset.getInt())){
				downPID.setConstants(Constants.elevatorDownPidKp.getDouble(), 
						   			 Constants.elevatorDownPidKi.getDouble() + Constants.elevatorDownPidKiAlt.getDouble(),
						   			 Constants.elevatorDownPidKd.getDouble());
			}
			else{
				downPID.setConstants(Constants.elevatorDownPidKp.getDouble(), 
			   			 Constants.elevatorDownPidKi.getDouble(),
			   			 Constants.elevatorDownPidKd.getDouble());
				if(downSpeed < -0.3) downSpeed = -0.3;
			}
			
			setElevatorSpeed(downSpeed);
			if(downPID.isDone(currentPosition, Constants.elevatorDownPidDeadband.getInt())){
				setBrake(true);
				return true;
			}
			else return false;
		}
		else{
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
	public boolean getBrakePosition(){
		return brake;
	}
	public void presetElevatorEncoder(int pos){
		elevatorEncoder.set(pos);
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
	

