package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class WaitForGearOut extends Command {

	public WaitForGearOut() {

	}

	protected void initialize() {
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return !Robot.gearIntake.isGearInRobot();
	}

	protected void end() {

	}

	protected void interrupted() {
		end();
	}
}
