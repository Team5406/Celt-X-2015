package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.Solenoid;

public class CanHolder {
	private Solenoid canHolderContraction;
	private Solenoid canHolderExpansion;
	
	public CanHolder(){
		canHolderContraction = new Solenoid(Constants.canHolderContraction.getInt());
		canHolderExpansion = new Solenoid(Constants.canHolderExpansion.getInt());
	}
	public boolean getHolderOpen(){
		return canHolderExpansion.get();
	}
	public void setPosition(boolean expanded){
		canHolderContraction.set(!expanded);
		canHolderExpansion.set(expanded);
	}
	
}
