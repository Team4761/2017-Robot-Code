package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class Shooter extends Subsystem {

    private final double ROLLER_SPEED = -0.5;

    public void initDefaultCommand() {

    }

    /**
     * Spins up the motors to shoot
     * @deprecated
     */
    @Deprecated
    public void spinUp() {
        RobotMap.shooterRollerSpeedController.set(ROLLER_SPEED);
    }
    
    public void spinUp(double rollerSpeed) {
    	RobotMap.shooterRollerSpeedController.set(rollerSpeed);
    }

    public void stop() {
        RobotMap.shooterRollerSpeedController.stopMotor();
    }
}

