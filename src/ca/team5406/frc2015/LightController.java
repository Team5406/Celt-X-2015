package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.I2C;

public class LightController {
	
	private I2C rioduino;
	
	public static enum LightPatterns{
		off,
		chase;
	}

	public LightController(){
		rioduino = new I2C(I2C.Port.kMXP, 1);
	}
	
	public void setLightPattern(LightPatterns pattern){
		sendPacket(pattern);
	}
	
	private void sendPacket(LightPatterns pattern){
		rioduino.write(0, pattern.ordinal());
	}
	
}
