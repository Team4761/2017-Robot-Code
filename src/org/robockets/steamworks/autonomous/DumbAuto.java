package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.steamworks.drivetrain.DriveTime;

/**
 * @author Jake Backer
 */
public class DumbAuto extends CommandGroup {

	public DumbAuto() {
		addSequential(new DriveTime(8, 0.5));
	}
}
