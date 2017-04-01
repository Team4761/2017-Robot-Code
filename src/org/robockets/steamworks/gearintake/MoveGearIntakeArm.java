package org.robockets.steamworks.gearintake;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class MoveGearIntakeArm extends Command {

	private RelativeDirection.ZAxis direction;
	private double speed;
	private boolean useLimitSwitch = false;

	public MoveGearIntakeArm(RelativeDirection.ZAxis direction, double speed, boolean useLimitSwitch) {
		this.direction = direction;
		this.speed = speed;
		this.useLimitSwitch = useLimitSwitch;
	}

	public MoveGearIntakeArm(RelativeDirection.ZAxis direction, double speed) {
		new MoveGearIntakeArm(direction, speed, true);
	}

	protected void initialize() {
	}

	protected void execute() {
		Robot.gearIntake.moveGearIntakeArm(direction, speed);
	}

	protected boolean isFinished() {
		if (useLimitSwitch) {
			if (direction == RelativeDirection.ZAxis.UP) {
				return RobotMap.gearIntakeUpperLimitSwitch.get();
			} else {
				return RobotMap.gearIntakeLowerLimitSwitch.get();
			}
		}

		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
