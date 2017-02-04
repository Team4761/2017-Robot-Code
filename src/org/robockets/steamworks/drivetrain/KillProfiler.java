package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * Kills the profiler by force
 * @author Jake Backer
 */
public class KillProfiler extends Command {

	public KillProfiler() {
		requires(Robot.dummyProfilerSubsystem);
	}

	protected void initialize() {
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
