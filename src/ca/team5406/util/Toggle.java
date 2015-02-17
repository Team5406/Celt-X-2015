package ca.team5406.util;

public class Toggle {
	
	private boolean state;

	public Toggle(boolean start){
		state = start;
	}
	
	public void setTrue(){
		state = true;
	}
	
	public void setFalse(){
		state = false;
	}
	
	public void toggle(){
		state = !state;
	}
	
	public boolean get(){
		return state;
	}
	
	
	
}
