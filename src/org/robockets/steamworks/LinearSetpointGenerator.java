package org.robockets.steamworks;

import java.util.Iterator;
import java.util.function.Consumer;

public class LinearSetpointGenerator implements Iterator<Double> {
	/**
	 * Final setpoint for whatever it is you're controlling. Unit is whatever
	 * the unit is for the value returned from
	 * {@link edu.wpi.first.wpilibj.PIDSource#pidGet()} over seconds.
	 */
	private double targetPosition;
	/**
	 * How fast you want to go, in units per second.
	 */
	private double velocity;
	/**
	 * Initial state of the PID source.
	 */
	private double initialPosition;
	/**
	 * How long is it expected for the robot to follow this path, using
	 * displacement and velocity.
	 */
	private double time;
	/**
	 * Number of seconds between each setpoint.
	 */
	private final double stepLength = 0.02; //seconds
	/**
	 * Number of steps (setpoint changes) it will take to get from the initial
	 * position to the target position.
	 */
	private double stepCount;
	/**
	 * How much the setpoint changes every step.
	 */
	private double stepHeight;
	/**
	 * How far the robot will travel.
	 */
	private double totalDisplacement;
	/**
	 * Number of times the {@link #next()} method has been called.
	 */
	private double getNextCount;

	/**
	 * Given a velocity and a set point, this class will give setpoints to help you get to your destination.
	 * @param targetPosition Final setpoint for whatever it is you're
	 * controlling. Unit is whatever the unit is for the value returned from
	 * {@link edu.wpi.first.wpilibj.PIDSource#pidGet()} over seconds.
	 * @param initialPosition Initial state of the PID source.
	 * @param velocity How fast you want to go, in units per second.
	 */
	public LinearSetpointGenerator(double targetPosition, double velocity, double initialPosition) {
		this.velocity = velocity;
		this.initialPosition = initialPosition;
		this.targetPosition = targetPosition + this.initialPosition;

		this.totalDisplacement = this.targetPosition - this.initialPosition;
		this.time = (this.totalDisplacement / this.velocity);
		this.stepCount = this.time / this.stepLength;
		this.stepHeight = this.totalDisplacement / this.stepCount;
		//System.out.println(this.stepCount);
		this.getNextCount = 0;
	}

	@Override
	public void forEachRemaining(Consumer<? super Double> arg0) {
	}

	/**
	 * @return Are there any remaining setpoints in the path?
	 */
	@Override
	public boolean hasNext() {
		return !(getNextCount>=stepCount);
	}

	/**
	 * @return The next setpoint in the path.
	 */
	@Override
	public Double next() {
		if (getNextCount < this.stepCount) {
			getNextCount++;
		}
		return initialPosition + (getNextCount * stepHeight);
	}

	@Override
	public void remove() {
	}
	
}
