package org.robockets.steamworks.shooter;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
@Deprecated
public class ShooterListener extends Command {

	public ShooterListener() {
		requires(Robot.shooter);
	}

	protected void initialize() {

	}

	protected void execute() {
		/*double rightTriggerVal = OI.operatorJoystick.getRawAxis(3);

		if (rightTriggerVal != 0) {
			Robot.shooter.spinUp(1);
		} else {
			Robot.shooter.stop();
		}*/
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.shooter.stop();
	}

	protected void interrupted() {
		end();
	}
}
