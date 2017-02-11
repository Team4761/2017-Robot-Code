package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.command.WaitCommand;
import org.robockets.steamworks.drivetrain.DriveWithMP;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 */
public class AutoTest extends CommandGroup {
    public AutoTest() {
    	addSequential(new DriveWithMP(96, 128));
    	addSequential(new WaitCommand(2));
    	addSequential(new DriveWithMP(-96, -24));
    }
}
