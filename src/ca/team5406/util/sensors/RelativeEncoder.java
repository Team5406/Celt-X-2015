package ca.team5406.util.sensors;

import edu.wpi.first.wpilibj.Encoder;

public class RelativeEncoder extends Encoder {
	
	private int zero = 0;

	public RelativeEncoder(int a, int b) {
		super(a, b);
	}
	
	public RelativeEncoder(int a, int b, boolean invert){
		super(a, b, invert);
	}
	
	public void set(int position){
		zero = (super.get() - position);
	}
	
	public int get(){
		return (super.get() - zero);
	}
	
	public int getActual(){
		return super.get();
	}
	
	public void reset(){
		zero = super.get();
	}

}
