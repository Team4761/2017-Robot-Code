package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class Shooter extends Subsystem {

    private final double ROLLER_SPEED = 0.5;

    public void initDefaultCommand() {

    }

    /**
     * Spins up the motors to shoot
     */
    public void spinUp() {
        // Spin up motors
        RobotMap.rollerSpeedController1.set(ROLLER_SPEED);
        RobotMap.rollerSpeedController2.set(ROLLER_SPEED);
    }

    public void stop() {
        RobotMap.rollerSpeedController1.set(0);
        RobotMap.rollerSpeedController2.set(0);
    }
}

