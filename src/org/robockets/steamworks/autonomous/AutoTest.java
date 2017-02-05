package org.robockets.steamworks.autonomous;

import org.robockets.steamworks.drivetrain.DriveWithDDP;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTest extends CommandGroup {
    public AutoTest() {
    	addSequential(new DriveWithDDP(24, 8));
    	//addSequential(new DriveWithDDP(24, 4));
    }
}
