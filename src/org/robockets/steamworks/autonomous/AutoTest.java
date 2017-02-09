package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.command.WaitCommand;
import org.robockets.steamworks.drivetrain.MoveDrivePodWithMP;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class AutoTest extends CommandGroup {
    public AutoTest() {
    	addSequential(new MoveDrivePodWithMP(96, 128));
    	addSequential(new WaitCommand(2));
    	addSequential(new MoveDrivePodWithMP(-96, -24));
    }
}
