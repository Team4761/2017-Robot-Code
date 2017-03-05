package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * PIDOutput used for drivepods
 */
public class DrivePodOutput implements PIDOutput {

	private SpeedController c;

	/**
	 * @param c Speed controller
	 */
	public DrivePodOutput(SpeedController c) {
		this.c = c;
	}
	
	@Override
	public void pidWrite(double output) {
		c.set(output);
	}

}
