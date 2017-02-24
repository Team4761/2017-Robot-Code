package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;
import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.LinearSetpointGenerator;

/**
 * @author Jake Backer & some Brian
 */
public class Turn extends Command {
	
	private final double DIAMETER = 29;
	private double angle;
	private double distance;
	private double speed;
	private LinearSetpointGenerator leftLsg, rightLsg;
	
	
	public Turn(TurnType type, double angle, TurnControllerType turnControllerType) {
		this.angle = angle * (Math.PI / 180); // convert to radians
		this.distance = (DIAMETER / 2) * angle; // s = r * theta
		this.speed = 8;
	}
	
	public Turn(TurnType type, double angle, double speed) {
		this.angle = angle * (Math.PI / 180); // convert to radians
		this.distance = (DIAMETER / 2) * angle; // s = r * theta
		this.speed = (DIAMETER * (Math.PI / 360.0)) * speed;
	}

	protected void initialize() {
		Robot.drivetrain.enableEncoderPID();
		leftLsg = new LinearSetpointGenerator(distance, speed, RobotMap.leftEncoder.getDistance());
		rightLsg = new LinearSetpointGenerator(-distance, -speed, RobotMap.rightEncoder.getDistance());
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
	
	public enum TurnControllerType{
		ENCODER,
		GYRO;
	}
}
