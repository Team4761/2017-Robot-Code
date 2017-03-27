package org.robockets.steamworks.camera;

import edu.wpi.first.wpilibj.Timer;
import org.robockets.steamworks.Robot;

import java.util.Stack;

public class CVConstants {
	public static final double LOGITECH_C270_FOV = 45 /* degrees */;
	public static final double LOGITECH_C270_ANGLE_OFFSET = 1.5 /* degrees */;

	public static boolean SHOULD_RUN_VISION = false;

	private static double _offset = 4761;
	private static double lastValidOffset = 0;
	private static double lastValidOffsetUpdateTime = 0;

	public static boolean isValidOffset(double offset) {
		return Math.abs(offset) <= LOGITECH_C270_FOV / 2;
	}

	public static double getOffset() {
		synchronized(Robot.visionManager.visionLock) {
			if(isValidOffset(_offset)) return _offset;
			else if(Timer.getFPGATimestamp() - lastValidOffsetUpdateTime <= 2) return lastValidOffset;
			else return 4761;

		}
	}
	
	public static void setOffset(double offset) {
		synchronized(Robot.visionManager.visionLock) {
			_offset = offset;

			if(isValidOffset(offset)) {
				lastValidOffsetUpdateTime = Timer.getFPGATimestamp();
				lastValidOffset = _offset;
			}
		}
	}
}
