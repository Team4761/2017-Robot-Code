package org.robockets.steamworks;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

public class DrivePodOutput implements PIDOutput {

	private SpeedController c1, c2;
	
	public DrivePodOutput(SpeedController c1, SpeedController c2) {
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public void pidWrite(double output) {
		c1.set(output);
		c2.set(output);
	}

}
