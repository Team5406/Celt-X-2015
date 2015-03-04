package ca.team5406.util;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

public class MultiCamServer {
    private int sessionA;
    private int sessionB;
    private Image frame;
    
    private Camera selectedCamera;

    private enum Camera{
    	front,
    	back;
    }
	
	public MultiCamServer(){
		init();
	}
	
	private void init(){
		System.out.println("Starting Cameras");
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
	    sessionA = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	    sessionB = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	   
	    setCamera("front");
		System.out.println("Done Cameras");
	}
	
	public void setCamera(String camera){
		switch(camera){
			default:
			case "front":
			    NIVision.IMAQdxConfigureGrab(sessionA);
				selectedCamera = Camera.front;
				break;
			case "back":
			    NIVision.IMAQdxConfigureGrab(sessionB);
				selectedCamera = Camera.back;
				break;
		}
	}
	
	public void sendImage(){
		if(selectedCamera == Camera.back){
			NIVision.IMAQdxGrab(sessionB, frame, 1);
		}
		else{
			NIVision.IMAQdxGrab(sessionA, frame, 1);
		}
		//Attempt at compressing the image.
		NIVision.imaqSetImageSize(frame, 640, 480);
        CameraServer.getInstance().setImage(frame);
	}
	
	public void stop(){
        NIVision.IMAQdxStopAcquisition(sessionA);
        NIVision.IMAQdxStopAcquisition(sessionB);
	}
	
}
