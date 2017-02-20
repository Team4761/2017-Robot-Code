package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
    	/*rightStick = OI.attack3Left.getRawAxis(1);
		leftStick = OI.attack3Right.getRawAxis(1);
		rightStick *= 1 - OI.attack3Left.getRawAxis(2);
		leftStick *= 1 - OI.attack3Right.getRawAxis(2);

		RobotMap.robotDrive.tankDrive(leftStick, rightStick);*/
    	
    	if(ToggleDriveMode.isArcade) {
    		translate = OI.joystick.getRawAxis(1);
        	rotate = OI.joystick.getRawAxis(4);	
        	
        	RobotMap.robotDrive.arcadeDrive(translate, rotate);
        
        	
    	} else {
    		leftStick = OI.attack3Left.getRawAxis(1);
    		rightStick = OI.attack3Right.getRawAxis(1);
    		rightStick /= 2;
    		leftStick /= 2;
    		leftStick *= 1 - OI.attack3Left.getRawAxis(2);
    		rightStick *= 1 - OI.attack3Right.getRawAxis(2);

    		SmartDashboard.putNumber("left power", leftStick);
    		SmartDashboard.putNumber("right power", rightStick);
    		RobotMap.robotDrive.tankDrive(leftStick, rightStick);
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
