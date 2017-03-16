package org.robockets.steamworks.camera;

import org.robockets.steamworks.Robot;

public class CVConstants {
	public static final double LOGITECH_C270_FOV = 60 /* degrees */;
	
	private static double _offset = 0;
	
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
