package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.RobotMap;

/**
 * "Magic Carpet"
 * @author Jake Backer
 */
public class Conveyor extends Subsystem {

    private final double CONVEYOR_SPEED = 0.5;

    public void initDefaultCommand() {

    }

    /**
     * Move the conveyor belt in the specified direction
     * @param direction The direction in which to move the conveyor
     */
    public void moveConveyor(RelativeDirection.YAxis direction) {
        double multiplier = (direction==RelativeDirection.YAxis.FORWARD) ? 1 : -1;

        RobotMap.conveyorSpeedControllerOne.set(multiplier*CONVEYOR_SPEED);
        RobotMap.conveyorSpeedControllerTwo.set(multiplier*CONVEYOR_SPEED);
    }

    public void stop() {
        RobotMap.conveyorSpeedControllerOne.stopMotor();
        RobotMap.conveyorSpeedControllerTwo.stopMotor();
    }
}

