package ca.team5406.frc2015;

import edu.wpi.first.wpilibj.I2C;

public class LightController {
	
	private I2C rioduino;
	
	private int redVal = 0;
	private int greenVal = 255;
	private int blueVal = 0;
	private PixelLightPatterns pattern = PixelLightPatterns.rainbow;
	
	public static enum PixelLightPatterns{
		rainbow,
		red,
		green,
		blue,
		off;
	}

	public LightController(){
		rioduino = new I2C(I2C.Port.kMXP, 1);
	}
	
	public void setUnderglowColor(double r, double g, double b){
		redVal = (int)(r * 255);
		greenVal = (int)(g * 255);
		blueVal = (int)(b * 255);
	}
	
	public void setLightPattern(PixelLightPatterns pattern){
		this.pattern = pattern;
	}
	
	public void updateLights(){
		String WriteString = pattern.name() + ":" + (redVal) + ":" + (greenVal) + ":" + (blueVal);
		
		char[] CharArray = WriteString.toCharArray();
		byte[] WriteData = new byte[CharArray.length];
		
		for (int i = 0; i < CharArray.length; i++) {
			WriteData[i] = (byte) CharArray[i];
		}
		
		rioduino.transaction(WriteData, WriteData.length, null, 0);
	}
	
}
