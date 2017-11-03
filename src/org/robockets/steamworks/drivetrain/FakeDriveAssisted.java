package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 * @deprecated 
 */
@Deprecated
public class FakeDriveAssisted extends Command {

	private LinearSetpointGenerator leftLsg, rightLsg;

	public FakeDriveAssisted() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	}

	protected void execute() {
		double leftValue = -OI.joystick.getRawAxis(1);
		double rightValue = -OI.joystick.getRawAxis(5);

		leftValue = Math.floor(leftValue * 100) / 100;
		rightValue = Math.floor(rightValue * 100) / 100;

		leftValue = Math.round(leftValue);
		rightValue = Math.round(rightValue);

		// This could cause memory issues

		double leftLsgVelocity = leftValue < 0 ? -0.2 : 0.2;
		double rightLsgVelocity = rightValue < 0 ? -0.2 : 0.2;

		leftLsg = new LinearSetpointGenerator(leftValue, leftLsgVelocity, 0); // Assuming 0
		rightLsg = new LinearSetpointGenerator(rightValue, rightLsgVelocity, 0);

		System.out.println(leftLsg.hasNext());
		System.out.println(rightLsg.hasNext());
		if (leftLsg.hasNext() || rightLsg.hasNext()) {
			double newLeftValue = leftLsg.next();
			double newRightValue = rightLsg.next();

			System.out.println("Left: " + (Double.isNaN(newLeftValue) ? 0 : newLeftValue));
			System.out.println("Right: " + (Double.isNaN(newRightValue) ? 0 : newRightValue));

			Robot.drivetrain.driveTank(newLeftValue , newRightValue);
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.leftPodSpeedPID.disable();
		Robot.drivetrain.rightPodSpeedPID.disable();
	}

	protected void interrupted() {
		end();
	}
}
