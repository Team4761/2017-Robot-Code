package org.robockets.steamworks.camera;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.robockets.steamworks.RobotMap;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Webcam {
	private int exposure = 1;
	
	private SendableChooser<Resolution> resolutionChooser;
	
	private UsbCamera camera;
	private CvSink cvSink;
	private CvSource processedOutputStream;
	
	private Mat source;
	private Mat output;
	
	private static Webcam instance;
	
	private Webcam() {
		camera = CameraServer.getInstance().startAutomaticCapture();
		
		resolutionChooser = new SendableChooser<Resolution>();
		resolutionChooser.addDefault("320x240", new Resolution(320, 240));
		resolutionChooser.addObject("640x480", new Resolution(640, 480));
		resolutionChooser.addObject("160x120", new Resolution(160, 120));
		SmartDashboard.putData(RobotMap.SmartDashboardKey.kVideoFeedResolution, resolutionChooser);
		updateResolution();
		
		cvSink = CameraServer.getInstance().getVideo();
		Resolution currentResolution = resolutionChooser.getSelected();
		processedOutputStream = CameraServer.getInstance().putVideo("Processed", currentResolution.width, currentResolution.height);
		
		source = new Mat();
		output = new Mat();
		
		SmartDashboard.putData("Video feed resolution", resolutionChooser);
		SmartDashboard.putNumber(RobotMap.SmartDashboardKey.kCameraExposure, 1);
	}
	
	public static Webcam getInstance() {
		if(instance == null) instance = new Webcam();
		return instance;
	}
	
	private Resolution lastResolution;
	private void updateResolution() {
		Resolution resolution = resolutionChooser.getSelected();
		if(!resolution.equals(lastResolution)) {
			camera.setResolution(resolution.width, resolution.height);
			lastResolution = resolution;
		}
	}
	
	private int lastExposure;
	private void updateExposure() {
		exposure = (int) SmartDashboard.getNumber(RobotMap.SmartDashboardKey.kCameraExposure, 1);
		if(exposure != lastExposure) {
			camera.setExposureManual(exposure);
			lastExposure = exposure;
		}
	}
	
	private void processFrame() {
		cvSink.grabFrame(source);
		Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
		processedOutputStream.putFrame(output);
	}
	
	public void startThread() {
		Thread t = new Thread() {
			@Override
			public void run() {
				while(!Thread.interrupted()) {
					updateResolution();
					updateExposure();
					processFrame();
				}
			}
		};
		
		t.start();
	}
	
	private class Resolution {
		public final int width;
		public final int height;
		
		public Resolution(int width, int height) {
			this.width  = width;
			this.height = height;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Resolution) {
				Resolution r = (Resolution) obj;
				return r.height == this.height && r.width == this.width;
			} else return false;
		}
	}
}
