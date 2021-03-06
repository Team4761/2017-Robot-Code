package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * Controlled teleop drive command
 * @author Jake Backer & a little bit of Brian Shin
 */
public class Joyride extends Command {

    private double translate;
    private double rotate;
    
    public Joyride() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    }

    protected void execute() {
		translate = OI.joystick.getRawAxis(1);
		rotate = OI.joystick.getRawAxis(4);
        	        	
		RobotMap.robotDrive.arcadeDrive(-translate, -rotate);
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
