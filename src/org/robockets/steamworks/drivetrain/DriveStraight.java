package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        requires(Robot.drivetrain);
        this.speed = speed;
        this.distance = distance;
    }

    protected void initialize() {
        System.out.println("Driving Straight...");
        Robot.drivetrain.enableEncoderPID();
    	Robot.drivetrain.resetEncoders();
    	leftLsg = new LinearSetpointGenerator(distance, speed, RobotMap.leftEncoder.getDistance());
    	rightLsg = new LinearSetpointGenerator(distance, speed, RobotMap.rightEncoder.getDistance());
    }

    protected void execute() {
        if (leftLsg.hasNext() || rightLsg.hasNext()) {
            Robot.drivetrain.leftPodPID.setSetpoint(leftLsg.next());
            Robot.drivetrain.rightPodPID.setSetpoint(rightLsg.next());
        }
    }

    protected boolean isFinished() {
        SmartDashboard.putBoolean("Encoders on Target", Robot.drivetrain.encodersOnTarget());
        return (Robot.drivetrain.encodersOnTarget() && (!leftLsg.hasNext() && !rightLsg.hasNext())) || Robot.drivetrain.getAverageCurrent() > 40;
    }

    protected void end() {
        System.out.println("Finished Driving Straight");
        DriverStation.reportWarning("Encoders after driving straight L: " + RobotMap.leftEncoder.get() + " R: " + RobotMap.rightEncoder.get(), false);
        Robot.drivetrain.disableEncoderPID();
        Robot.drivetrain.stop();
    }

    protected void interrupted() {
        end();
    }
}
