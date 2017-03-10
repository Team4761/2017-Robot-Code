package org.robockets.steamworks.camera;

public class CVConstants {
	public static final double LOGITECH_C270_FOV = 60 /* degrees */;
	
	private static double _offset = 0;
	
	public static double getOffset() {
		return _offset;
	}
	
	protected static void setOffset(double offset) {
		_offset = offset;
	}
}
