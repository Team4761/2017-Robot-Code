package org.robockets.steamworks.gearintake;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class GearIntakeJoystickListener extends Command {

	private final double CONTROLLER_WEIGHT = 0.5;

	private RelativeDirection.ZAxis direction;

	public GearIntakeJoystickListener() {
		requires(Robot.gearIntake);
	}

	protected void initialize() {
	}

	protected void execute() {
		double value = -OI.backupJoystick.getRawAxis(1)*CONTROLLER_WEIGHT;
		System.out.println("Upper: " + RobotMap.gearIntakeUpperLimitSwitch.get());
		System.out.println("Lower: " + RobotMap.gearIntakeLowerLimitSwitch.get());

		if (value < 0) {
			direction = RelativeDirection.ZAxis.DOWN;
		} else {
			direction = RelativeDirection.ZAxis.UP;
		}

		if (direction == RelativeDirection.ZAxis.UP) {
			if (!RobotMap.gearIntakeUpperLimitSwitch.get()) {
				Robot.gearIntake.moveGearIntakeArm(RelativeDirection.ZAxis.UP, value);
			} else {
				Robot.gearIntake.moveGearIntakeArm(RelativeDirection.ZAxis.UP, 0.1);
			}
		} else {
			if (!RobotMap.gearIntakeLowerLimitSwitch.get()) {
				Robot.gearIntake.moveGearIntakeArm(RelativeDirection.ZAxis.UP, value); // Ignore this...
			} else {
				Robot.gearIntake.moveGearIntakeArm(RelativeDirection.ZAxis.UP, -0.1);
			}
		}
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
