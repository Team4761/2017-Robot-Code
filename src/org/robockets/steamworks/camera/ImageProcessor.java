package org.robockets.steamworks.camera;

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

public class ImageProcessor implements VisionPipeline {


	public double angleOffset;
	public static CvSource processedOutputStream = CameraServer.getInstance().putVideo("Vision (processed)", 480, 320);

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
		ArrayList<MatOfPoint> ret = new ArrayList<>();
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint firstContour = contours.get(i);
			if(!contoursAreEqual(firstContour, contours.get(i + 1))) {
				ret.add(firstContour);
			}
		}
		return ret;
	}

	public static ArrayList<MatOfPoint> filterContours(ArrayList<MatOfPoint> contours) {
		ArrayList<MatOfPoint> tapeContours = new ArrayList<MatOfPoint>();
		for(int i = 0; i < contours.size(); i++) {
			MatOfPoint contour = contours.get(i);
			if(isPegTape(contour)) tapeContours.add(contour);
			if(tapeContours.size() >= 2) break;
		}
		return tapeContours;
	}

	@Override
	public void process(Mat image) {
		Mat binarized = binarize(image);
		ArrayList<MatOfPoint> contours = filterContours(getContours(binarized));
		if(contours.size() != 2) {
			processedOutputStream.putFrame(binarized);
			angleOffset = 4761;
			return;
		}
		/* Rect leftRect = Imgproc.boundingRect(contours.get(0));
		Rect rightRect = Imgproc.boundingRect(contours.get(1));
		if(leftRect.tl().x > rightRect.tl().x) {
			Rect temp = leftRect;
			leftRect = rightRect;
			rightRect = temp;
		} */

		Rect rect0 = Imgproc.boundingRect(contours.get(0));
		Rect rect1 = Imgproc.boundingRect(contours.get(1));
		Rect leftRect = (rect0.tl().x < rect1.tl().x) ? rect0 : rect1;
		Rect rightRect = (rect0.br().x > rect1.br().x) ? rect0 : rect1;

		double farLeft  = leftRect.tl().x;
		double farRight = rightRect.tl().x;
		double midpoint = (farLeft + farRight) / 2d;

		double pixelOffset = midpoint - (image.width() / 2d);
		double pixelToAngleFactor = CVConstants.LOGITECH_C270_FOV / image.width();

		//Imgproc.rectangle(binarized, leftRect.tl(), leftRect.br(), new Scalar(0, 255, 0), 2);
		//Imgproc.rectangle(binarized, rightRect.tl(), rightRect.br(), new Scalar(0, 255, 0), 2);
		Imgproc.rectangle(binarized, leftRect.tl(), rightRect.br(), new Scalar(255, 255, 0), 3);

		angleOffset = pixelOffset * pixelToAngleFactor  *10;
		processedOutputStream.putFrame(binarized);
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

	public static boolean contoursAreEqual(MatOfPoint c1, MatOfPoint c2) {
		return Imgproc.contourArea(c1) == Imgproc.contourArea(c2);
	}

	public static void copyList(ArrayList src, ArrayList dst) {
		for(Object obj : src) dst.add(obj);
	}

	public static ArrayList removeDuplicates(ArrayList<MatOfPoint> list) {
		for(int i = 0; i < list.size(); i++) {
			ArrayList<MatOfPoint> temp = new ArrayList<MatOfPoint>();
			copyList(list, temp);
			temp.remove(i);
			for(MatOfPoint contour : temp) {
				//if()
			}
		}
		return null;
	}
}
