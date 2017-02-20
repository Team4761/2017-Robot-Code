package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.commons.RelativeDirection;

/**
 * "Fill" elevator at quarter speed
 * @author Jake Backer
 */
public class MaxFillElevator extends CommandGroup {

	public MaxFillElevator() {
		addSequential(new MoveElevator(RelativeDirection.ZAxis.UP, 0.25));
	}
}
