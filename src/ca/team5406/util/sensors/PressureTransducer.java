package ca.team5406.util.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class PressureTransducer extends AnalogInput {

	public PressureTransducer(int channel) {
		super(channel);
	}

	public int getPsi(){
		return (int)((super.getVoltage() - 0.5) * 18.75);
	}
}
