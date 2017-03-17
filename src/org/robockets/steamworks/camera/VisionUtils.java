package org.robockets.steamworks.camera;

import java.util.ArrayList;
import java.util.Collections;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

public class VisionUtils {
	
	public static Mat binarize(Mat image) {
		/// Convert to grayscale
		Mat grayed = new Mat();
		Imgproc.cvtColor(image, grayed, Imgproc.COLOR_BGR2GRAY);
		
		/// Binarize with Otsu's method
		Mat thresholded = new Mat();
		Imgproc.threshold(grayed, thresholded, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

		/// Open (erode, followed by a dilate)
		Mat opened = new Mat();
		Imgproc.morphologyEx(thresholded, opened, Imgproc.MORPH_OPEN, new Mat());
		
		return opened;
	}

	public static boolean contoursAreEqual(MatOfPoint c1, MatOfPoint c2) {
		return Imgproc.contourArea(c1) == Imgproc.contourArea(c2);
	}
	
	public static ArrayList<MatOfPoint> getContours(Mat binaryImage) {
		/// Find contours
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(binaryImage, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Collections.sort(contours, new ContourAreaComparator());
		return contours;
	}

	public static double getRectangleRatio(double width, double height) {
		double larger = Math.max(width, height);
		double smaller = Math.min(width, height);
		return (double) larger / (double) smaller;
	}

}
