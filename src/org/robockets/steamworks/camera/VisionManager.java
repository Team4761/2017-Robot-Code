package org.robockets.steamworks.camera;

import org.robockets.steamworks.RobotMap;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class VisionManager implements VisionRunner.Listener<ImageProcessor> {

	private ImageProcessor imageProcessor;
	private VisionThread visionThread;
	private CvSource processedVideoStream;
	
	public Object visionLock = new Object();
	
	public VisionManager() {
		imageProcessor = new ImageProcessor();
		visionThread = new VisionThread(RobotMap.visionCamera, imageProcessor, this);
		processedVideoStream = CameraServer.getInstance().putVideo("Vision camera (processed)", 480, 320);
	}
	
	public void startProcessing() {
		visionThread.start();
	}

	@Override
	public void copyPipelineOutputs(ImageProcessor pipeline) {
		synchronized(visionLock) {
			processedVideoStream.putFrame(pipeline.output);
			CVConstants.setOffset(pipeline.angleOffset);
		}
	}

}
