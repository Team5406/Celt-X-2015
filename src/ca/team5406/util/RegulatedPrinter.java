package ca.team5406.util;

import edu.wpi.first.wpilibj.Timer;

public class RegulatedPrinter {
	
	private Timer printTimer = new Timer();
	private double period = 0;
	
	public RegulatedPrinter(double period){
		this.period = period;
		printTimer.reset();
		printTimer.start();
	}
	
	public void print(String text){
		if(printTimer.get() >= period){
			printTimer.reset();
			System.out.println(text);
		}
	}
	
	public void directPrint(String text){
		System.out.println(text);
	}
	
}
