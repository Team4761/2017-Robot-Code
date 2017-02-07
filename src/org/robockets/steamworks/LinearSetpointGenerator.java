package org.robockets.steamworks;

import java.util.Iterator;
import java.util.function.Consumer;

public class LinearSetpointGenerator implements Iterator<Double> {
	private double targetPosition;
	private double velocity;
	private double initialPosition;
	private double time;
	private final double stepLength = 0.02 /* seconds */;
	private double stepCount;
	private double stepHeight;
	private double totalDisplacement;
	private double getNextCount;

	/**
	 * Given a velocity and a set point, this class will give setpoints to help
	 * you get to your destination.
	 * @param targetPosition Final setpoint for whatever it is you're
	 * controlling. Unit is whatever the unit is for the value returned from
	 * {@link edu.wpi.first.wpilibj.PIDSource#pidGet()} over seconds.
	 * @param initialPosition Initial state of the PID source.
	 * @param velocity How fast you want to go, in units per second.
	 */
	public LinearSetpointGenerator(double targetPosition, double velocity, double initialPosition) {
		this.targetPosition = targetPosition;
		this.velocity = velocity;
		this.initialPosition = initialPosition;
		
		this.totalDisplacement = this.targetPosition - this.initialPosition;
		this.time = (long) (this.totalDisplacement / this.velocity);
		this.stepCount = this.time / this.stepLength;
		this.stepHeight = this.totalDisplacement / this.stepCount;
		this.getNextCount = 0;
	}

	@Override
	public void forEachRemaining(Consumer<? super Double> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasNext() {
		return stepCount == getNextCount; //TODO: Check if this is correct or if it "forgets" the last number
	}

	@Override
	public Double next() {
		return initialPosition + (getNextCount++ * stepHeight);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	

}