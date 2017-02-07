package org.robockets.steamworks.autonomous;

import org.robockets.steamworks.drivetrain.MoveDrivePodWithMP;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class AutoTest extends CommandGroup {
    public AutoTest() {
    	addSequential(new MoveDrivePodWithMP());
    	addSequential(new MoveDrivePodWithMP());
    }
}
