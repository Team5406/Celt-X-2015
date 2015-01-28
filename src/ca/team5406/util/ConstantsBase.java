package ca.team5406.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public abstract class ConstantsBase {

	private ConstantsBase(){}
	
	private static ArrayList<Constant> constants = new ArrayList<Constant>();
	private static final String FILE_PATH = "Constants.txt";
	
	public static void updateConstantsFromFile(){
		try {
			File file = new File(FILE_PATH);
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    
			while((line = br.readLine()) != null){
				String[] parts = line.replaceAll(" ", "").split(":");
				String name = parts[0];
				double value = Double.parseDouble(parts[1]);
				
				if(name.startsWith("#")) continue; //Marked to skip
				if(name.length() <= 2) continue; //Name too short
				
				if(writeConstant(name, value)){
					System.out.println("Setting " + name + " to " + value);
				}
				else{
					System.out.println("Error: Could not find constant: " + value);
				}
			}
			
		    br.close();
		    fr.close();
		}
		catch (Exception e){
			System.out.println("Error: Could not load constants!");
		}
	}
	
	public static boolean writeConstant(String key, double value){
		for(Constant constant : constants){
			if(constant.getName() == key){
				constant.setValue(value);
				return true;
			}
		}
		return false;
	}
	
	
	public static class Constant{
		
		private String key;
		private double value;
		
		public Constant(String key, double value){
			this.key = key;
			this.value = value;
			constants.add(this);
		}
		public void setValue(double value){
			this.value = value;
		}
		
		public String getName(){
			return key;
		}
		
		public int getInt(){
			return (int)(value);
		}
		
		public double getDouble(){
			return value;
		}
	}
	
}

