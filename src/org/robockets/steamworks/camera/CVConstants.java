package org.robockets.steamworks.camera;

import org.robockets.steamworks.Robot;

public class CVConstants {
	public static final double LOGITECH_C270_FOV = 45 /* degrees */;
	public static final double LOGITECH_C270_ANGLE_OFFSET = 1.5 /* degrees */;

	public static boolean SHOULD_RUN_VISION = false;

	private static double _offset = 4761;
	
	public static double getOffset() {
		synchronized(Robot.visionManager.visionLock) {
			return _offset;
		}
	}
	
	public static void setOffset(double offset) {
		synchronized(Robot.visionManager.visionLock) {
			_offset = offset;
		}
	}
}
