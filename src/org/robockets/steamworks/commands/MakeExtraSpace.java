package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class MakeExtraSpace extends Command {

	public MakeExtraSpace() {
		
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.conveyor.moveConveyor(RelativeDirection.YAxis.FORWARD, 0.25);
		Robot.elevator.moveElevator(RelativeDirection.ZAxis.UP, 0.25);
	}

	protected boolean isFinished() {
		return RobotMap.elevatorBreakbeamSensor.get();
		//return false;
	}

	protected void end() {
		Robot.conveyor.stop();
	}

	protected void interrupted() {
		end();
	}
}
