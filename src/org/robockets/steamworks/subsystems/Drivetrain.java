package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class Drivetrain extends Subsystem {

    public void initDefaultCommand() {

    }

    public void driveArcade(double translate, double rotate) {
        RobotMap.robotDrive.arcadeDrive(translate, rotate);
    }

    public void driveTank(double leftValue, double rightValue) {
        RobotMap.robotDrive.tankDrive(leftValue, rightValue);
    }

    public void stop() {
        driveArcade(0,0);
    }

}

