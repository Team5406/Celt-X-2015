package ca.team5406.util;

public class PID {
	
	private double kP = 0.0;
	private double kI = 0.0;
	private double kD = 0.0;
	private double accumI = 0.0;
	
	private double previousError = 0.0;
	
	private double desiredPosition = 0.0;
	
	public PID(double kP, double kI, double kD){
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}
	
	public PID(){}
	
	public void initPID(double desiredPosition, double currentPosition){
		previousError = desiredPosition - currentPosition;
		this.desiredPosition = desiredPosition;
		accumI = 0.0;
	}
	
	public double calcSpeed(double currentPosition){
		
		double currentError = desiredPosition - currentPosition;
		
		double valP = kP * currentError;
		double valI = accumI;
		double valD = kD * (currentError - previousError);
		accumI += kI;
		
		double speed = valP + valI - valD;

		previousError = currentError;
		
		return speed;
	}
	
	private int timesTrue = 0;
	public boolean isDone(double currentPosition, double deadband){
		if(Math.abs(desiredPosition - currentPosition) <= deadband){
			if(timesTrue >= 10){
				return true;
			}
			timesTrue++;
		}
		else timesTrue = 0;
		
		return false;
	}
	
	public void setCosntants(double kP, double kI, double kD){
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}
	
	
	
}
