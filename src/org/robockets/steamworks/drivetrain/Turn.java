package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.TurnType;

/**
 * @author Jake Backer & some Brian
 */
public class Turn extends Command {

	private TurnType type;
	private double angle;

	/**
	 * Turn command to control turning with gyro
	 * @param type Type of turn, absolute or relative
	 * @param angle Desired angle
	 */
	public Turn(TurnType type, double angle) {
		this.type = type;
		this.angle = angle;
	}

	protected void initialize() {
		//angle = SmartDashboard.getNumber("New Gyro Angle(AbsoluteOrRelative)", 0);
		if (type == TurnType.ABSOLUTE) {
			Robot.drivetrain.absoluteTurn(angle);
		} else {
			Robot.drivetrain.relativeTurn(angle);
		}
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return Robot.drivetrain.gyroOnTarget();
	}

	protected void end() {
		Robot.drivetrain.stop();
	}

	protected void interrupted() {
		end();
	}
}
