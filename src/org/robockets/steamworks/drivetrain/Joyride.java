package org.robockets.steamworks.drivetrain;

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
    	Robot.leftDDP.updateCurrentPosition(RobotMap.leftEncoder.getDistance());
    	Robot.rightDDP.updateCurrentPosition(RobotMap.rightEncoder.getDistance());
    }

    protected void execute() { 	
    	if(!Robot.drivetrain.isEncoderPIDEnabled()) {
    		if(ToggleDriveMode.isArcade) {
	    		//RobotMap.robotDrive.arcadeDrive(OI.joystick, 1, OI.joystick, 4);
	    		RobotMap.robotDrive.arcadeDrive(OI.joystick.getRawAxis(1), OI.joystick.getRawAxis(4));
	    		OI.joystick.setRumble(Joystick.RumbleType.kRightRumble, 0.0f);
	    	} else {
	    		RobotMap.robotDrive.tankDrive(OI.joystick, 1, OI.joystick, 5);
	    		OI.joystick.setRumble(Joystick.RumbleType.kRightRumble, 0.25f);
	    	}
    	} else {
    		double leftJoyRaw = -OI.joystick.getRawAxis(1);
    		double rightJoyRaw = -OI.joystick.getRawAxis(5);
    		double distanceFactor = 0.02 * 5;
    		Robot.leftDDP.updateParameters(RobotMap.leftEncoder.getDistance() + (leftJoyRaw * Robot.MAX_SPEED * distanceFactor), (leftJoyRaw * Robot.MAX_SPEED));
    		Robot.rightDDP.updateParameters(RobotMap.rightEncoder.getDistance() + (rightJoyRaw * (Robot.MAX_SPEED * distanceFactor)), (rightJoyRaw * Robot.MAX_SPEED));
    		//RobotMap.robotDrive.tankDrive(Robot.leftDDP.getNewPosition(), Robot.rightDDP.getNewPosition());
    		Robot.drivetrain.leftPodPID.setSetpoint(Robot.leftDDP.getNewPosition());
    		Robot.drivetrain.rightPodPID.setSetpoint(Robot.rightDDP.getNewPosition());
    	}
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.stop();
        Robot.drivetrain.leftPodPID.disable();
        Robot.drivetrain.rightPodPID.disable();
    }

    protected void interrupted() {
        end();
    }
}
