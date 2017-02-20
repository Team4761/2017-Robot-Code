package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * Command to drive straight with the help of a gyro
 *
 */
public class DriveStraight extends Command {
    private double speed;
    private double distance;
    private LinearSetpointGenerator leftLsg, rightLsg;
    
    public DriveStraight(double speed, double distance) {
        this.speed = speed;
        this.distance = distance;
    }

    protected void initialize() {
    	Robot.drivetrain.enableEncoderPID();
    	leftLsg = new LinearSetpointGenerator(distance, speed, RobotMap.leftEncoder.getDistance());
    	rightLsg = new LinearSetpointGenerator(distance, speed, RobotMap.rightEncoder.getDistance());
    }

    protected void execute() {
        Robot.drivetrain.leftPodPID.setSetpoint(leftLsg.next());
        Robot.drivetrain.rightPodPID.setSetpoint(rightLsg.next());
    }

    protected boolean isFinished() {
        return !leftLsg.hasNext() || !rightLsg.hasNext();
    }

    protected void end() {
        Robot.drivetrain.disableEncoderPID();
        Robot.drivetrain.stop();
    }

    protected void interrupted() {
        end();
    }
}
