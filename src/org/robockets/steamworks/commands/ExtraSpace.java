package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class ExtraSpace extends Command {

	public ExtraSpace() {

	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.conveyor.moveConveyor(RelativeDirection.YAxis.FORWARD);
	}

	protected boolean isFinished() {
		return RobotMap.breakbeamSensor.get();
	}

	protected void end() {
		Robot.conveyor.stop();
	}

	protected void interrupted() {
		end();
	}
}
