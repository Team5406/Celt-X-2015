package ca.team5406.util;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

public class MultiCamServer {
    private int sessionA;
    private Image frameA;
    private int sessionB;
    private Image frameB;
    
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
		frameA = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	    sessionA = NIVision.IMAQdxOpenCamera("cam1", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	    
		frameB = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
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
			NIVision.IMAQdxGrab(sessionB, frameB, 1);
	        CameraServer.getInstance().setImage(frameB);
		}
		else{
			NIVision.IMAQdxGrab(sessionA, frameA, 1);
	        CameraServer.getInstance().setImage(frameA);
		}
	}
	
	public void stop(){
        NIVision.IMAQdxStopAcquisition(sessionA);
        NIVision.IMAQdxStopAcquisition(sessionB);
	}
	
}
