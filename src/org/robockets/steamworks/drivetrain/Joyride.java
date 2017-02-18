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

    private boolean xBoxController;
    private double translate;
    private double rotate;
    private double leftStick;
    private double rightStick;
    
    public Joyride(boolean xBoxController) {
    	requires(Robot.drivetrain);
    	this.xBoxController = xBoxController;
    }

    protected void initialize() {
    	xBoxController = false;
    }

    protected void execute() {
    	if(xBoxController) {
    		translate = OI.joystick.getRawAxis(1);
        	rotate = OI.joystick.getRawAxis(4);	
        	
        	if(ToggleDriveMode.isArcade) {
        		RobotMap.robotDrive.arcadeDrive(translate, rotate);
        		OI.joystick.setRumble(RumbleType.kRightRumble, 0.0);
        	} else {
        		RobotMap.robotDrive.tankDrive(OI.joystick, 1, OI.joystick, 5); // People who use tank drive don't deserve speed scaling
        		OI.joystick.setRumble(RumbleType.kRightRumble, 0.25);
        	}
    	} else {
    		leftStick = OI.attack3Left.getRawAxis(1);
    		rightStick = OI.attack3Right.getRawAxis(1);
    		RobotMap.robotDrive.tankDrive(-leftStick, -rightStick);
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
