package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 * Drivetrain subsystem
 */
public class Drivetrain extends Subsystem {

    public void initDefaultCommand() {

    }
   
    /**
     * Basic method to control the movement of the robot 'arcade' style
     * @param translate Motor speed to move forward: -1 to 1
     * @param rotate Rotation speed: -1 to 1
     */
    public void driveArcade(double translate, double rotate) {
        RobotMap.robotDrive.arcadeDrive(translate, rotate);
    }

    /**
     * Basic method to control the movement of the robot 'tank' style
     * @param leftValue Motor speed for the left side of the robot: -1 to 1
     * @param rightValue Motor speed for the right side of the robot: -1 to 1
     */
    public void driveTank(double leftValue, double rightValue) {
        RobotMap.robotDrive.tankDrive(leftValue, rightValue);
    }
    
    /**
     * A method to stop the drivetrain.
     */
    public void stop() {
        driveArcade(0,0);
    }

}

