package org.robockets.steamworks.camera;

import org.robockets.steamworks.RobotMap;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class VisionManager implements VisionRunner.Listener<ImageProcessor> {

	private ImageProcessor imageProcessor;
	private VisionThread visionThread;
	private CvSource processedVideoStream;
	
	private static UsbCamera visionCamera;
	
	public Object visionLock = new Object();
	
	private static VisionManager instance;
	
	private VisionManager() {
		visionCamera = CameraServer.getInstance().startAutomaticCapture("VISION CAMERA", 1);
		visionCamera.setExposureManual(0);
		
		imageProcessor = new ImageProcessor();
		visionThread = new VisionThread(visionCamera, imageProcessor, this);
		processedVideoStream = CameraServer.getInstance().putVideo("PROCESSED", 480, 320);
	}
	
	public static VisionManager getInstance() {
		if(instance == null) {
			instance = new VisionManager();
		}
		return instance;
	}
	
	public void startProcessing() {
		visionThread.start();
	}

	@Override
	public void copyPipelineOutputs(ImageProcessor pipeline) {
		synchronized(visionLock) {
			if(pipeline.output != null) {
				processedVideoStream.putFrame(pipeline.output);
			}
			else {
				System.out.println("Oh snap! There were not enough reasonable contours!");
			}
			CVConstants.setOffset(pipeline.angleOffset);
		}
	}

}
