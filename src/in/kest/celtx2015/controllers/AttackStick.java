package in.kest.celtx2015.controllers;

public class AttackStick extends Controller {
	
	public static final int TRIGGER = 1;

	public AttackStick(int port) {
		super(port);
	}
	
	public double getMainX(){
		return super.getRawAxis(0);
	}
	
	public double getMainY(){
		return super.getRawAxis(1);
	}

}
