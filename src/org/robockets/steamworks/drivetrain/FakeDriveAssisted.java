package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class FakeDriveAssisted extends Command {

	double previousLeft = 0;
	double previousRight = 0;

	final double VELOCITY = 0.5;

	public FakeDriveAssisted() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
	}

	protected void execute() {
		double leftValue = -OI.joystick.getRawAxis(1);
		double rightValue = -OI.joystick.getRawAxis(5);

		SmartDashboard.putNumber("Raw Left Value", leftValue);
		SmartDashboard.putNumber("Raw Right Value", rightValue);

		//leftValue = Math.floor(leftValue * 100) / 100;
		//rightValue = Math.floor(rightValue * 100) / 100;

		//leftValue = Math.round(leftValue);
		//rightValue = Math.round(rightValue);

		SmartDashboard.putNumber("Left Value", leftValue);
		SmartDashboard.putNumber("Right Value", rightValue);

		//double leftDiff = Math.floor((leftValue - previousLeft)*100)/100;
		//double rightDiff = Math.floor((rightValue - previousRight)*100)/100;
		double leftDiff = leftValue - previousLeft;
		double rightDiff = rightValue - previousRight;

		SmartDashboard.putNumber("Left Diff", leftDiff);
		SmartDashboard.putNumber("Right Diff", rightDiff);

		double newLeftValue = (leftDiff*VELOCITY)+previousLeft;
		double newRightValue = (rightDiff*VELOCITY)+previousRight;

		SmartDashboard.putNumber("New Left Value", newLeftValue);
		SmartDashboard.putNumber("New Right Value", newRightValue);

		previousLeft = newLeftValue;
		previousRight = newRightValue;

		Robot.drivetrain.driveTank(newLeftValue , newRightValue);
		//Robot.drivetrain.driveTank(leftValue, rightValue);

	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
