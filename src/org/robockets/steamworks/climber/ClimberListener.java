package org.robockets.steamworks.climber;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class ClimberListener extends Command { // NOTE: THE DRIVER HAS CONTROL OF THIS

	public ClimberListener() {
		requires(Robot.climber);
	}

	protected void initialize() {

	}

	protected void execute() {
		/*double rightTriggerVal = OI.joystick.getRawAxis(3);
		double leftTriggerVal = OI.joystick.getRawAxis(2);

		if (rightTriggerVal != 0 && !Robot.climber.isStalling()) {
			Robot.climber.setMotor(-1); // FIXME: Make sure this is right
		} else if (leftTriggerVal != 0 && !Robot.climber.isStalling()) {
			Robot.climber.setMotor(-1);
		} else {
			Robot.climber.setMotor(0);
		}*/

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {

	}

	protected void interrupted() {
		end();
	}
}
