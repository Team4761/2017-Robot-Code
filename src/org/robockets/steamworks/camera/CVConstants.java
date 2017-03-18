package org.robockets.steamworks.camera;

import org.robockets.steamworks.Robot;

import java.util.Stack;

public class CVConstants {
	public static final double LOGITECH_C270_FOV = 45 /* degrees */;
	public static final double LOGITECH_C270_ANGLE_OFFSET = 1.5 /* degrees */;

	public static boolean SHOULD_RUN_VISION = false;

	private static double _offset = 4761;

	private static Stack<Double> validAngles = new Stack<>();

	public static boolean isValidOffset(double offset) {
		return Math.abs(offset) <= LOGITECH_C270_FOV / 2;
	}

	public static double getOffset() {
		synchronized(Robot.visionManager.visionLock) {
			return _offset;
		}
	}

	public static double getLastValidOffset() {
		return validAngles.peek();
	}
	
	public static void setOffset(double offset) {
		synchronized(Robot.visionManager.visionLock) {
			_offset = offset;

			if(isValidOffset(offset)) {
				validAngles.push(offset);
			}
		}
	}
}
