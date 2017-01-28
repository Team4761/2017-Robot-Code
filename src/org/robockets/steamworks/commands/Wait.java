package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Wait an amount of time
 * @author Jake Backer
 */
public class Wait extends Command {

	private double time;

	/**
	 * Initializer for wait
	 * @param time Time in seconds
	 */
	public Wait(double time) {
		this.time = time;
	}

	protected void initialize() {
		setTimeout(time);
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {

	}

	protected void interrupted() {
		end();
	}
}
