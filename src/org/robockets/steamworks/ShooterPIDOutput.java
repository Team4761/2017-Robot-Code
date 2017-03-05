package org.robockets.steamworks;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

public class ShooterPIDOutput implements PIDOutput {

	private SpeedController c;

	public ShooterPIDOutput (SpeedController c) {
		this.c = c;
	}

	@Override
	public void pidWrite(double output) {
		c.set(output);
	}
}
