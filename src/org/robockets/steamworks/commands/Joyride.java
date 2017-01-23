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
        //double left = OI.joystick.getRawAxis(PortNumbers.JOYSTICK_LEFT_STICK);
        //double right = OI.joystick.getRawAxis(PortNumbers.JOYSTICK_RIGHT_STICK);

        RobotMap.robotDrive.tankDrive(OI.joystick, 1, OI.joystick, 5);
        
        if (OI.joystick.getRawButton(1) && tanking) { // Bind this to one of the 4 colored buttons
        	OI.joystick.setRumble(RumbleType.kLeftRumble, 0.75);
        	OI.joystick.setRumble(RumbleType.kLeftRumble, 0);
        	RobotMap.robotDrive.tankDrive(OI.joystick, 1, OI.joystick, 5);
        } else if (OI.joystick.getRawButton(1) && !tanking) {
        	OI.joystick.setRumble(RumbleType.kLeftRumble, 0.75);
        	OI.joystick.setRumble(RumbleType.kLeftRumble, 0);
        	OI.joystick.setRumble(RumbleType.kLeftRumble, 0.75);
        	OI.joystick.setRumble(RumbleType.kLeftRumble, 0);
        	RobotMap.robotDrive.arcadeDrive(OI.joystick, 1, OI.joystick, 5);
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
