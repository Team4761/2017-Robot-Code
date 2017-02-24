package org.robockets.steamworks;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kRate;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return counter.getRate();
	}
		
}
