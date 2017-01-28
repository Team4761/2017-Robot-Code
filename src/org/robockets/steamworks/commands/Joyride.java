package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * Controlled teleop drive command
 * @author Jake Backer & a little bit of Brian Shin
 */
public class Joyride extends Command {

    private double speed;

    /**
     * @param speed Speed multiplier
     */
    public Joyride(double speed) {
        requires(Robot.drivetrain);
        this.speed = speed;
    }

    protected void initialize() {
    }

    protected void execute() {
    	if(ToggleDriveMode.isArcade) {
    		//RobotMap.robotDrive.arcadeDrive(OI.joystick, 1, OI.joystick, 4);
    		RobotMap.robotDrive.arcadeDrive(OI.joystick.getRawAxis(1), OI.joystick.getRawAxis(4));
    		OI.joystick.setRumble(Joystick.RumbleType.kRightRumble, 0.0f);
    	} else {
    		RobotMap.robotDrive.tankDrive(OI.joystick, 1, OI.joystick, 5);
    		OI.joystick.setRumble(Joystick.RumbleType.kRightRumble, 0.25f);
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.stop();
    }

    protected void interrupted() {
        end();
    }
}
