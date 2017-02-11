package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.RobotMap;

/**
 * "Magic Carpet"
 * @author Jake Backer
 */
public class Conveyor extends Subsystem {

    public void initDefaultCommand() {

    }

    /**
     * Move the conveyor belt in the specified direction
     * @param direction The direction in which to move the conveyor
     */
    public void moveConveyor(RelativeDirection.YAxis direction, double speed) {
        double multiplier = (direction==RelativeDirection.YAxis.FORWARD) ? 1 : -1;

        RobotMap.conveyorSpeedControllerOne.set(multiplier*speed);
        RobotMap.conveyorSpeedControllerTwo.set(multiplier*speed);
    }

    public void stop() {
        RobotMap.conveyorSpeedControllerOne.stopMotor();
        RobotMap.conveyorSpeedControllerTwo.stopMotor();
    }
}

