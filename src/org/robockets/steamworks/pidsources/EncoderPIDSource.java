package org.robockets.steamworks.pidsources;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

public class EncoderPIDSource implements PIDSource {
	
	private Encoder encoder;
	private double factor;

	/**
	 * Encoder PID Source
	 * @param encoder The encoder that you wish to read values from.
	 * @param factor A multiplier to manipulate the encoder output.
	 */
	
	public EncoderPIDSource(Encoder encoder, double factor) {
		this.encoder = encoder;
		this.factor = factor;
		this.encoder.setDistancePerPulse(0.05555);
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
		return this.encoder.getDistance();
	}

}
