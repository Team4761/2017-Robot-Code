package org.robockets.steamworks.camera;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.robockets.steamworks.RobotMap;

public class ImageProcessor implements VisionPipeline {

	public double angleOffset;

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
	
	public static ArrayList<MatOfPoint> getContours(Mat binaryImage) {

		/// Canny
		Mat cannyOut = new Mat();
		Imgproc.Canny(binaryImage, cannyOut, 100, 200);
		
		/// Find contours
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(cannyOut, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Collections.sort(contours, new ContourAreaComparator());
		return contours;
	}

	public static ArrayList<MatOfPoint> filterContours(ArrayList<MatOfPoint> contours) {
		removeDuplicates(contours);
		ArrayList<MatOfPoint> tapeContours = new ArrayList<MatOfPoint>();
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint contour = contours.get(i);
			if(isPegTape(contour)) tapeContours.add(contour);
			if(tapeContours.size() >= 2) break;
		}
		return tapeContours;
	}

	public static ArrayList<MatOfPoint> removeDuplicates(ArrayList<MatOfPoint> list) {
		Collections.sort(list, new ContourAreaComparator());
		ArrayList<MatOfPoint> ret = new ArrayList<>();
		int duplicateCount = 0;
		for(int i = 0; i < list.size(); i++) {
			MatOfPoint mainObject = list.get(i);
			MatOfPoint neighbor;
			try {
				neighbor = list.get(i + 1);
			} catch (IndexOutOfBoundsException e) {
				neighbor = list.get(0);
			}

			if(!contoursAreEqual(mainObject, neighbor)) {
				ret.add(mainObject);
			}
			else {
				duplicateCount++;
			}
		}
		System.out.println("Duplicate count: " + duplicateCount);
		return ret;
	}

	public static boolean contoursAreEqual(MatOfPoint c1, MatOfPoint c2) {
		return Math.floor(Imgproc.contourArea(c1)) == Math.floor(Imgproc.contourArea(c2));
	}

	@Override
	public void process(Mat image) {
		Mat binarized = binarize(image);
		ArrayList<MatOfPoint> contours = getContours(binarized);

		double leftMost = 0;
		double rightMost = 0;
		if(contours.size() < 2) {
			RobotMap.processedOutputStream.putFrame(binarized);
			return;
		}
		for(MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			double contourLeft = rect.tl().x;
			double contourRight = rect.br().x;
			if(contourLeft < leftMost) leftMost = contourLeft;
			if(contourRight > rightMost) rightMost = contourRight;
		}
		/*
		if(contours.size() != 2) {
			processedOutputStream.putFrame(binarized);
			angleOffset = 4761;
			return;
		}
		Rect leftRect = Imgproc.boundingRect(contours.get(0));
		Rect rightRect = Imgproc.boundingRect(contours.get(1));
		if(leftRect.tl().x > rightRect.tl().x) {
			Rect temp = leftRect;
			leftRect = rightRect;
			rightRect = temp;
		}

		Rect rect0 = Imgproc.boundingRect(contours.get(0));
		Rect rect1 = Imgproc.boundingRect(contours.get(1));
		Rect leftRect = (rect0.tl().x < rect1.tl().x) ? rect0 : rect1;
		Rect rightRect = (rect0.br().x > rect1.br().x) ? rect0 : rect1;

		double farLeft  = leftRect.tl().x;
		double farRight = rightRect.tl().x; */
		double midpoint = (leftMost + rightMost) / 2d;

		double pixelOffset = midpoint - (image.width() / 2d);
		double pixelToAngleFactor = CVConstants.LOGITECH_C270_FOV / image.width();

		//Imgproc.rectangle(binarized, leftRect.tl(),  leftRect.br(),  new Scalar(0,   255, 0), 2);
		//Imgproc.rectangle(binarized, rightRect.tl(), rightRect.br(), new Scalar(0,   255, 0), 2);
		//Imgproc.rectangle(binarized, leftRect.tl(),  rightRect.br(), new Scalar(255, 255, 0), 3);

		angleOffset = pixelOffset * pixelToAngleFactor;
		RobotMap.processedOutputStream.putFrame(binarized);
	}
	
	private static double getRectangleRatio(double width, double height) {
		double larger = Math.max(width, height);
		double smaller = Math.min(width, height);
		return (double) larger / (double) smaller;
	}
	
	private static boolean isPegTape(MatOfPoint contour) {
		
		MatOfPoint2f mop2f = new MatOfPoint2f();
		contour.convertTo(mop2f, CvType.CV_32FC2);
		RotatedRect minAreaRect = Imgproc.minAreaRect(mop2f);
		final double ratio = getRectangleRatio(minAreaRect.size.width, minAreaRect.size.height);
		if(ratio < 2.3 || ratio > 2.7) return false;
		
		double rectArea = minAreaRect.size.width * minAreaRect.size.height;
		double contourArea = Imgproc.contourArea(contour);
		double fullness = contourArea / rectArea;
		if(fullness < 0.8) return false;

		return true;
	}
}


