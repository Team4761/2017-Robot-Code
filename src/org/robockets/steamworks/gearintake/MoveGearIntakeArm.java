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
	private boolean isForever = true;
	private double timeout;

	public MoveGearIntakeArm(RelativeDirection.ZAxis direction, double speed, boolean useLimitSwitch) {
		this.direction = direction;
		this.speed = speed;
		this.useLimitSwitch = useLimitSwitch;
	}

	public MoveGearIntakeArm(RelativeDirection.ZAxis direction, double speed) {
		new MoveGearIntakeArm(direction, speed, true);
	}

	public MoveGearIntakeArm(RelativeDirection.ZAxis direction, double speed, boolean useLimitSwitch, double timeout) {
		this.direction = direction;
		this.speed = speed;
		this.useLimitSwitch = useLimitSwitch;
		isForever = false;
		this.timeout = timeout;
	}

	protected void initialize() {
		if (!isForever) {
			setTimeout(timeout);
		}
	}

	protected void execute() {
		Robot.gearIntake.moveGearIntakeArm(direction, speed);
	}

	protected boolean isFinished() {
		/*if (!isForever) {
			return isTimedOut();
		}*/

		if (useLimitSwitch) {
			if (direction == RelativeDirection.ZAxis.UP) {
				return RobotMap.gearIntakeUpperLimitSwitch.get() || (!isForever && isTimedOut());
			} else {
				return RobotMap.gearIntakeLowerLimitSwitch.get() || (!isForever && isTimedOut());
			}
		}

		return false;
	}

	protected void end() {
		Robot.gearIntake.stopArm();
	}

	protected void interrupted() {
		end();
	}
}
