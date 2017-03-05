package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class Elevator extends Subsystem {

	public void initDefaultCommand() {

	}

	public void moveElevator (RelativeDirection.ZAxis direction, double speed) {
		if (direction == RelativeDirection.ZAxis.UP) {
			RobotMap.elevatorSpeedController.set(speed);
		} else {
			RobotMap.elevatorSpeedController.set(-speed);
		}
	}

	public void stop() {
		RobotMap.elevatorSpeedController.set(0);
	}
}

