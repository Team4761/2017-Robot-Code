package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
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

		// This could cause memory issues
		leftLsg = new LinearSetpointGenerator(leftValue, 0.2, 0); // Assuming 0
		rightLsg = new LinearSetpointGenerator(rightValue, 0.2, 0);
		if (leftLsg.hasNext() || rightLsg.hasNext()) {
			Robot.drivetrain.driveTank(leftLsg.next(), rightLsg.next());
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
