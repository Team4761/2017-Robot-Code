package org.robockets.steamworks.pidsources;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class CounterRatePIDSource implements PIDSource {

	private Counter counter;
	
	public CounterRatePIDSource(Counter counter) {
		this.counter = counter;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kRate;
	}

	@Override
	public double pidGet() {
		return counter.getRate();
	}
		
}
