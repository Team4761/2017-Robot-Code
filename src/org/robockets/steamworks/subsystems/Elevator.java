package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class Elevator extends Subsystem {

	private final double ELEVATOR_SPEED = 0.5;

	public void initDefaultCommand() {

	}

	public void moveElevator (RelativeDirection.ZAxis direction) {
		if (direction == RelativeDirection.ZAxis.UP) {
			RobotMap.elevatorSpeedController.set(ELEVATOR_SPEED);
		} else {
			RobotMap.elevatorSpeedController.set(-ELEVATOR_SPEED);
		}
	}

	public void stop() {
		RobotMap.elevatorSpeedController.set(0);
	}
}

