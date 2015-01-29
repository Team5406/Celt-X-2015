package ca.team5406.frc2015;

public class DrivePID {
	
	private Drive drive;
	//Declare DriveTo, TurnTo PIDs

	public DrivePID(Drive drive){
		this.drive = drive;
		
		//Init PIDs with constants
	}
	
	public void resetDriveToPos(){
		
	}
	
	public boolean driveToPos(int pos){
		return false;
	}
	
	public void resetTurnToAngle(){
		
	}
	
	public boolean turnToAngle(double angle){
		return false;
	}
	
}
