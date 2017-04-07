package org.robockets.steamworks.lights;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class SendLight extends Command {

	private int value = 56;

	public SendLight(int value) {
		this.value = value;
	}

	protected void initialize() {
		Robot.ledSubsystem.cylon(value);
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
