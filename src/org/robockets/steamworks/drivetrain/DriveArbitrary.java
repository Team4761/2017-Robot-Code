package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * Command to drive straight with the help of a gyro
 *
 */
public class DriveArbitrary extends Command {
	private double leftSpeed;
	private double leftDistance;
	private double rightSpeed;
	private double rightDistance;
	private LinearSetpointGenerator leftLsg, rightLsg;

	public DriveArbitrary(double leftSpeed, double leftDistance, double rightSpeed, double rightDistance) {
		requires(Robot.drivetrain);
		this.leftSpeed = leftSpeed;
		this.leftDistance = leftDistance;
		this.rightSpeed = rightSpeed;
		this.rightDistance = rightDistance;
	}

	protected void initialize() {
		System.out.println("Driving Arbitrarily...");
		Robot.drivetrain.enableEncoderPID();
		Robot.drivetrain.resetEncoders();
		leftLsg = new LinearSetpointGenerator(leftDistance, leftSpeed, RobotMap.leftEncoder.getDistance());
		rightLsg = new LinearSetpointGenerator(rightDistance, rightSpeed, RobotMap.rightEncoder.getDistance());
	}

	protected void execute() {
		if (leftLsg.hasNext() || rightLsg.hasNext()) {
			Robot.drivetrain.leftPodPID.setSetpoint(leftLsg.next());
			Robot.drivetrain.rightPodPID.setSetpoint(rightLsg.next());
		}
	}

	protected boolean isFinished() {
		SmartDashboard.putBoolean("Encoders on Target", Robot.drivetrain.encodersOnTarget());
		return Robot.drivetrain.encodersOnTarget() && (!leftLsg.hasNext() && !rightLsg.hasNext());
	}

	protected void end() {
		System.out.println("Finished Driving Arbitrarily");
		Robot.drivetrain.disableEncoderPID();
		Robot.drivetrain.stop();
	}

	protected void interrupted() {
		end();
	}
}
