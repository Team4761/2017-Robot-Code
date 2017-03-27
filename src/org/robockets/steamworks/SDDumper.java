package org.robockets.steamworks;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Dumps values for pieces of hardware onto SmartDashboard.
 * 
 * https://www.youtube.com/watch?v=LYpwB-oicPI
 * 
 * @author Simon
 */
public class SDDumper {
	
	public static void dumpEncoder(String prefix, Encoder encoder) {
		SmartDashboard.putNumber(prefix + " distance",    encoder.getDistance());
		SmartDashboard.putNumber(prefix + " raw",         encoder.get());
	}

	public static void dumpMisc() {
		SmartDashboard.putNumber("Uptime", Timer.getFPGATimestamp());
	}
	
	public static void dumpPidController(String prefix, PIDController controller) {
		SmartDashboard.putNumber(prefix + " D value", controller.getD());
		SmartDashboard.putNumber(prefix + " error", controller.getError());
		SmartDashboard.putNumber(prefix + " F value", controller.getF());
		SmartDashboard.putNumber(prefix + " I value", controller.getI());
		SmartDashboard.putNumber(prefix + " P value", controller.getP());
		SmartDashboard.putNumber(prefix + " setpoint", controller.getSetpoint());
		SmartDashboard.putBoolean(prefix + " is enabled", controller.isEnabled());
	}
}
