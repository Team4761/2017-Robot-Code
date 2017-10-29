package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
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
	
	private final double DIAMETER = 27.0;

	private final double VISION_TIMEOUT = 3.0;

	private double angle;
	private double distance;
	private double speed;
	private LinearSetpointGenerator leftLsg, rightLsg;

	private TurnType type;
	private double currentLeftP;
	private double currentLeftI;
	private double currentLeftD;

	private double currentRightP;
	private double currentRightI;
	private double currentRightD;

	private boolean resetPidWhenDone = false;

	public Turn(TurnType type, double speed) {
		requires(Robot.drivetrain);

		double angle = 0;

		this.type = type;

		this.angle = angle * (Math.PI / 180); // convert to radians
		this.distance = (DIAMETER / 2.0) * this.angle; // s = r * theta
		this.speed = (DIAMETER * (Math.PI / 360.0)) * speed;
	}

	public Turn(TurnType type, double angle, double speed) {
		requires(Robot.drivetrain);

		this.angle = angle * (Math.PI / 180); // convert to radians
		this.distance = (DIAMETER / 2.0) * this.angle; // s = r * theta
		this.speed = (DIAMETER * (Math.PI / 360.0)) * speed;
	}

	public Turn(TurnType type, double angle, double speed, double P, double I, double D) {

		currentLeftP = Robot.drivetrain.leftPodDistancePID.getP();
		currentLeftI = Robot.drivetrain.leftPodDistancePID.getI();
		currentLeftD = Robot.drivetrain.leftPodDistancePID.getD();

		currentRightP = Robot.drivetrain.rightPodDistancePID.getP();
		currentRightI = Robot.drivetrain.rightPodDistancePID.getI();
		currentRightD = Robot.drivetrain.rightPodDistancePID.getD();

		Robot.drivetrain.leftPodDistancePID.setPID(P, I, D);
		Robot.drivetrain.rightPodDistancePID.setPID(-P, -I, -D);

		resetPidWhenDone = true;

		this.angle = angle * (Math.PI / 180.0); // convert to radians
		this.distance = (DIAMETER / 2.0) * this.angle; // s = r * theta
		this.speed = (DIAMETER * (Math.PI / 360.0)) * speed;
	}

	protected void initialize() {

		System.out.println("Turning...");
		if (type == TurnType.CAMERA) { // No other way to do this
			System.out.println("Turning With Vision");
			double visionAngle = CVConstants.getOffset();
			System.out.println("Vision Angle: " + visionAngle);
			if (visionAngle >= 1000) {
				System.out.println("Vision angle too large!! Setting to 0...");
				visionAngle = 0;
			}

			if (visionAngle < 0) {
				speed *= -1;
			}

			System.out.println("Offset: " + visionAngle);
			this.angle = visionAngle * (Math.PI / 180.0); // convert to radians
			this.distance = (DIAMETER / 2.0) * this.angle; // s = r * theta

			setTimeout(VISION_TIMEOUT);
		}

		// Could alter PID if needed
		Robot.drivetrain.resetEncoders();
		Robot.drivetrain.enableEncoderDistancePID();

		leftLsg = new LinearSetpointGenerator(distance, speed, RobotMap.leftEncoder.getDistance());
		rightLsg = new LinearSetpointGenerator(-distance, -speed, RobotMap.rightEncoder.getDistance());
	}

	protected void execute() {
		Robot.drivetrain.leftPodDistancePID.setSetpoint(leftLsg.next());
		Robot.drivetrain.rightPodDistancePID.setSetpoint(rightLsg.next());
	}

	protected boolean isFinished() {
		if (type == TurnType.CAMERA) {
			return (Robot.drivetrain.distanceEncodersOnTarget() && (!leftLsg.hasNext() && !rightLsg.hasNext())) || isTimedOut();
		}
		return Robot.drivetrain.distanceEncodersOnTarget() && (!leftLsg.hasNext() && !rightLsg.hasNext());
	}

	protected void end() {
		DriverStation.reportWarning("Encoders after turning L: " + RobotMap.leftEncoder.get() + " R: " + RobotMap.rightEncoder.get(), false);
		Robot.drivetrain.disableEncoderDistancePID();
		Robot.drivetrain.stop();

		if (resetPidWhenDone) {
			Robot.drivetrain.leftPodDistancePID.setPID(currentLeftP, currentLeftI, currentLeftD);
			Robot.drivetrain.rightPodDistancePID.setPID(currentRightP, currentRightI, currentRightD);
		}

	}

	protected void interrupted() {
		end();
	}
}
