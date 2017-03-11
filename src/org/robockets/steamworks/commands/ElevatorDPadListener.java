package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class ElevatorDPadListener extends Command {

	public ElevatorDPadListener() {
		requires(Robot.elevator);
	}

	protected void initialize() {
	}

	protected void execute() {
		int povVal = OI.backupJoystick.getPOV(); // Read in degrees

		if (povVal == 0) {
			Robot.elevator.moveElevator(RelativeDirection.ZAxis.UP, 1);
		} else if (povVal == 180) {
			Robot.elevator.moveElevator(RelativeDirection.ZAxis.DOWN, 1);
		} else {
			// Left trigger
			double leftTriggerVal = OI.backupJoystick.getRawAxis(2);

			if (leftTriggerVal != 0) {
				Robot.elevator.moveElevator(RelativeDirection.ZAxis.UP, leftTriggerVal);
			} else {
				Robot.elevator.stop();
			}
		}

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.elevator.stop();
	}

	protected void interrupted() {
		end();
	}
}
