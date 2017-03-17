package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;
import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.camera.CVConstants;

/**
 * @author Jake Backer & some Brian
 */
public class Turn extends Command {
	
	private final double DIAMETER = 29.0;
	private double angle;
	private double distance;
	private double speed;
	private LinearSetpointGenerator leftLsg, rightLsg;

	private double currentLeftP;
	private double currentLeftI;
	private double currentLeftD;

	private double currentRightP;
	private double currentRightI;
	private double currentRightD;

	private boolean resetPidWhenDone = false;
	
	public Turn(TurnType type, double angle, double speed) {
		requires(Robot.drivetrain);

		if (type == TurnType.CAMERA) {
			// Get angle from camera
			angle = CVConstants.getOffset();
		}

		this.angle = angle * (Math.PI / 180); // convert to radians
		this.distance = (DIAMETER / 2.0) * this.angle; // s = r * theta
		this.speed = (DIAMETER * (Math.PI / 360.0)) * speed;
	}

	public Turn(TurnType type, double angle, double speed, double P, double I, double D) {

		currentLeftP = Robot.drivetrain.leftPodPID.getP();
		currentLeftI = Robot.drivetrain.leftPodPID.getI();
		currentLeftD = Robot.drivetrain.leftPodPID.getD();

		currentRightP = Robot.drivetrain.rightPodPID.getP();
		currentRightI = Robot.drivetrain.rightPodPID.getI();
		currentRightD = Robot.drivetrain.rightPodPID.getD();

		Robot.drivetrain.leftPodPID.setPID(P, I, D);
		Robot.drivetrain.rightPodPID.setPID(-P, -I, -D);

		resetPidWhenDone = true;

		// This is awful...
		if (type == TurnType.CAMERA) {
			// Get angle from camera
			angle = CVConstants.getOffset();
		}

		this.angle = angle * (Math.PI / 180); // convert to radians
		this.distance = (DIAMETER / 2.0) * this.angle; // s = r * theta
		this.speed = (DIAMETER * (Math.PI / 360.0)) * speed;
	}

	protected void initialize() {
		// Could alter PID if needed
		Robot.drivetrain.resetEncoders();
		Robot.drivetrain.enableEncoderPID();

		leftLsg = new LinearSetpointGenerator(distance, speed, RobotMap.leftEncoder.getDistance());
		rightLsg = new LinearSetpointGenerator(-distance, -speed, RobotMap.rightEncoder.getDistance());
	}

	protected void execute() {
		Robot.drivetrain.leftPodPID.setSetpoint(leftLsg.next());
		Robot.drivetrain.rightPodPID.setSetpoint(rightLsg.next());
	}

	protected boolean isFinished() {
		return Robot.drivetrain.encodersOnTarget() && (!leftLsg.hasNext() && !rightLsg.hasNext());
	}

	protected void end() {
		Robot.drivetrain.disableEncoderPID();
		Robot.drivetrain.stop();

		if (resetPidWhenDone) {
			Robot.drivetrain.leftPodPID.setPID(currentLeftP, currentLeftI, currentLeftD);
			Robot.drivetrain.rightPodPID.setPID(currentRightP, currentRightI, currentRightD);
		}

	}

	protected void interrupted() {
		end();
	}
}
