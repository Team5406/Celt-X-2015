package ca.team5496.util;

public class Functions {
	
	public static double applyJoystickFilter(double val){
		val = applyDeadband(val, 0.1);
		val = limitValue(val, 1.0);
		val = squareValueKeepSign(val);
		
		return val;
	}
	
	public static double squareValueKeepSign(double val){
		return Math.abs(val) * val;
	}
	
	public static double limitValue(double val, double limit){
		return (val > limit ? limit : (val < -limit ? -limit : val));
	}
	
	public static double applyDeadband(double val, double deadband){
		return (Math.abs(val) < deadband ? 0 : val);
	}
}
