package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.commons.RelativeDirection;
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
	private double radius1;
	private double theta;
	private double innerDist;
	private double outerDist;
	private double innerSpeed;
	private double outerSpeed;
	private RelativeDirection.XAxis direction;

	@Deprecated
	public DriveArbitrary(double leftSpeed, double leftDistance, double rightSpeed, double rightDistance) {
		requires(Robot.drivetrain);
		this.leftSpeed = leftSpeed;
		this.leftDistance = leftDistance;
		this.rightSpeed = rightSpeed;
		this.rightDistance = rightDistance;
	}

	public DriveArbitrary(double radius1, double theta, RelativeDirection.XAxis direction) {
		requires(Robot.drivetrain);
		this.radius1 = radius1;
		this.theta = theta;
		this.direction = direction;
	}

	protected void initialize() {
		System.out.println("Driving Arbitrarily...");
		Robot.drivetrain.enableEncoderPID();
		Robot.drivetrain.resetEncoders();
		innerDist = ((2 * Math.PI) / 360) * (radius1 * theta);
		outerDist = ((2 * Math.PI) / 360) * (radius1 + 29) * theta;
		innerSpeed = 24;
		outerSpeed = innerSpeed + ((29 * innerSpeed)/ radius1);

		if(direction == RelativeDirection.XAxis.LEFT) {
			leftDistance = innerDist;
			leftSpeed = innerSpeed;
			rightDistance = outerDist;
			rightSpeed = outerSpeed;
		} else {
			leftDistance = outerDist;
			leftSpeed = outerSpeed;
			rightDistance = innerDist;
			rightSpeed = innerSpeed;
		}

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
