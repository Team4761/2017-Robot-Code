package org.robockets.steamworks.pidsources;

import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderPIDSource implements PIDSource {
	
	private double factor;

	/**
	 * Encoder PID Source
	 * @param factor A multiplier to manipulate the encoder output.
	 */
	
	public EncoderPIDSource(double factor) {
		this.factor = factor;
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return RobotMap.leftEncoder.get() * factor;
	}

}
