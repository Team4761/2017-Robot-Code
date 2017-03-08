package org.robockets.steamworks.shooter;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class KillShooter extends Command {

	public KillShooter() {
		requires(Robot.shooter);
	}

	protected void initialize() {
		Robot.shooter.stop();
		Robot.elevator.stop();
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
