package org.robockets.steamworks;

import java.util.Iterator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionPlanner {
	private double targetPosition;
	private double velocity;
	private double initialPosition;
	private double distanceRemaining;
	private double startTime;
	private double time;
	private final double stepLength = 0.02 /* seconds */;
	private double stepHeight;
	private double totalDisplacement;
	private double setpoint;

	/**
	 * Given a velocity and a set point, this class will give setpoints to help
	 * you get to your destination.
	 * @param targetPosition Final setpoint for whatever it is you're
	 * controlling. Unit is whatever the unit is for the value returned from
	 * {@link edu.wpi.first.wpilibj.PIDSource#pidGet()} over seconds.
	 * @param initialPosition Initial state of the PID source.
	 * @param velocity How fast you want to go, in units per second.
	 */
	public MotionPlanner(double targetPosition, double velocity, double initialPosition) {
		this.targetPosition = targetPosition;
		this.velocity = velocity;
		this.initialPosition = initialPosition;
		this.totalDisplacement = targetPosition - initialPosition;
		this.time = (long) (this.totalDisplacement / velocity);
		double stepCount = this.time / this.stepLength;
		this.stepHeight = this.totalDisplacement / stepCount;
	}

	/**
	 * Sets the motion planner's start time. Call this just before you start
	 * updating setpoints.
	 */
	public void start() {
		startTime = Timer.getFPGATimestamp();
	}

	/**
	 * Returns how long the motion planner has been running for, in seconds.
	 * @return Time since {@link #start()} was called.
	 */
	public double getRunningTime() {
		return Timer.getFPGATimestamp() - startTime;
	}

	/**
	 * Sets a local variable that tells how far away we are from the desired
	 * setpoint.
	 */
	private void updateDistanceRemaining() {
		distanceRemaining = targetPosition - setpoint;
	}

	/**
	 * @return The setpoint that you should give to your
	 * {@link edu.wpi.first.wpilibj.PIDController PID controller}.
	 */
	public double getSetpoint() {
		updateDistanceRemaining();
		if(distanceRemaining > 0) {
			setpoint += stepHeight;
			return setpoint;
		}
		return 0;
	}

	/**
	 * @return Is the remaining distance within the tolerance range of the
	 * target setpoint?
	 */
	public boolean isInPosition() {
		updateDistanceRemaining();
		SmartDashboard.putNumber("distance remaining", distanceRemaining);
		SmartDashboard.putBoolean("is in position", distanceRemaining <= 0);
		return distanceRemaining <= 0;
	}
}