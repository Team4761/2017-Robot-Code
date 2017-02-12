package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
    private double translate;
    private double rotate;

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
    	translate = OI.joystick.getRawAxis(1);
    	rotate = OI.joystick.getRawAxis(4);
    	
    	if(OI.joystick.getRawButton(5)) {
    		translate *= 0.5;
    		rotate *= 0.5;
    	}

    	if(ToggleDriveMode.isArcade) {
    		//RobotMap.robotDrive.arcadeDrive(OI.joystick, 1, OI.joystick, 4);
    		RobotMap.robotDrive.arcadeDrive(translate, rotate);
    		OI.joystick.setRumble(RumbleType.kRightRumble, 0.0);
    	} else {
    		RobotMap.robotDrive.tankDrive(OI.joystick, 1, OI.joystick, 5); // People who use tank drive don't deserve speed scaling
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
