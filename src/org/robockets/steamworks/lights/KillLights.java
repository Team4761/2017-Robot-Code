package org.robockets.steamworks.lights;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class KillLights extends Command {

	public KillLights() {

	}

	protected void initialize() {
		System.out.println("Killing lights...");
		Robot.ledSubsystem.cylon(56);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}

	protected void interrupted() {
		end();
	}
}
