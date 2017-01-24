package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.PortNumbers;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * Controlled teleop drive command
 * @author Jake Backer
 */
public class Joyride extends Command {

    private double speed;
    private boolean tanking;
    private boolean tanking2;

    /**
     * @param speed Speed multiplier
     */
    public Joyride(double speed) {
        requires(Robot.drivetrain);
        this.speed = speed;
    }

    protected void initialize() {
    	tanking = true; // Default to tank drive, even though it is WRONG
    }

    protected void execute() {
    	if(ToggleDriveMode.isArcade) {
    		//RobotMap.robotDrive.arcadeDrive(OI.joystick, 1, OI.joystick, 4);
    		RobotMap.robotDrive.arcadeDrive(OI.joystick.getRawAxis(1), OI.joystick.getRawAxis(4));
    		OI.joystick.setRumble(RumbleType.kRightRumble, 0.0);
    	}
    	else {
    		RobotMap.robotDrive.tankDrive(OI.joystick, 1, OI.joystick, 5);
    		OI.joystick.setRumble(RumbleType.kRightRumble, 0.25);
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
