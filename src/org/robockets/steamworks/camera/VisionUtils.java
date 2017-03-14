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
		/// Canny
		Mat cannyOut = new Mat();
		Imgproc.Canny(binaryImage, cannyOut, 100, 200);
		
		/// Find contours
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(cannyOut, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Collections.sort(contours, new ContourAreaComparator());
		ArrayList<MatOfPoint> ret = new ArrayList<>();
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint firstContour = contours.get(i);
			if(!contoursAreEqual(firstContour, contours.get(i + 1))) {
				ret.add(firstContour);
			}
		}
		return ret;
	}

	public static double getRectangleRatio(double width, double height) {
		double larger = Math.max(width, height);
		double smaller = Math.min(width, height);
		return (double) larger / (double) smaller;
	}
	
	public static ArrayList<MatOfPoint> removeDuplicateContours(ArrayList<MatOfPoint> list) {
		for(int i = 0; i < list.size(); i++) {
			ArrayList<MatOfPoint> temp = new ArrayList<MatOfPoint>();
			for(MatOfPoint contour : list) temp.add(contour);
			temp.remove(i);
			for(MatOfPoint contour : temp) {
				//if()
			}
		}
		return null;
	}

}
