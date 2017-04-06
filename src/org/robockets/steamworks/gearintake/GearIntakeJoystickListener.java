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
		System.out.println("Initializing gear intake listener!!!");
	}


	protected void execute() {

		double value = -OI.operatorJoystick.getRawAxis(1)*CONTROLLER_WEIGHT*0.5; // Left Stick

		if (OI.operatorJoystick.getRawButton(9)) {
			value *= 2;
		}

		if (value < 0) {
			direction = RelativeDirection.ZAxis.DOWN;
		} else if (value > 0) {
			direction = RelativeDirection.ZAxis.UP;
		} else {
			direction = RelativeDirection.ZAxis.UP;
			value = 0.12;
		}

		if (direction == RelativeDirection.ZAxis.UP) {

			if (RobotMap.gearIntakeUpperLimitSwitch.get()) {
				value = 0.11;
			}

			Robot.gearIntake.moveGearIntakeArm(RelativeDirection.ZAxis.UP, value);
		} else {

			if (RobotMap.gearIntakeLowerLimitSwitch.get()) {
				value = 0;
			}

			Robot.gearIntake.moveGearIntakeArm(RelativeDirection.ZAxis.UP, value);
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
