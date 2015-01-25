package in.kest.celtx2015.sensors;

import edu.wpi.first.wpilibj.Encoder;

public class ReletiveEncoder extends Encoder {
	
	private int zero = 0;

	public ReletiveEncoder(int a, int b) {
		super(a, b);
	}
	
	public ReletiveEncoder(int a, int b, boolean invert){
		super(a, b, invert);
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
