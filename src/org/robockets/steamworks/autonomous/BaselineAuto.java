package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.steamworks.drivetrain.DriveStraight;

/**
 * @author Simon Andrews
 */
public class BaselineAuto extends CommandGroup {

	public BaselineAuto() {
		addSequential(new DriveStraight(24, 200));
	}
}
