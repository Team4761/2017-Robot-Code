package org.robockets.steamworks.camera;

import java.util.Comparator;

import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

public class ContourAreaComparator implements Comparator<MatOfPoint> {

	public int compare(MatOfPoint arg0, MatOfPoint arg1) {
		return (int) (Imgproc.contourArea(arg1) - Imgproc.contourArea(arg0));
	}
}
