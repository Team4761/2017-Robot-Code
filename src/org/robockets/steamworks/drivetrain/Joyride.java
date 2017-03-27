package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private double leftStick;
    private double rightStick;
    
    public Joyride() {
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	ToggleDriveMode.isArcade = true;
    }

    protected void execute() {  	
    	if(ToggleDriveMode.isArcade) {
        	//RobotMap.robotDrive.arcadeDrive(OI.joystick); // "Cleaner" but its javadocs says its one-joystick arcade drive
        	
        	translate = OI.joystick.getRawAxis(1);
        	rotate = OI.joystick.getRawAxis(4);
        	        	
        	RobotMap.robotDrive.arcadeDrive(-translate, -rotate);
    	} else {
    		/*
    		leftStick = OI.attack3Left.getRawAxis(1);
    		rightStick = OI.attack3Right.getRawAxis(1);
    		leftStick /= -2;
    		rightStick /= -2;
    		leftStick *= 1 - OI.attack3Left.getRawAxis(2);
    		rightStick *= 1 - OI.attack3Right.getRawAxis(2);

    		RobotMap.robotDrive.tankDrive(leftStick, rightStick);*/
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
