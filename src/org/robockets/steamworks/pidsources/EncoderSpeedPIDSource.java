package org.robockets.steamworks.pidsources;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class EncoderSpeedPIDSource implements PIDSource {

	private Encoder encoder;

	public EncoderSpeedPIDSource(Encoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		encoder.setPIDSourceType(PIDSourceType.kRate);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kRate;
	}

	@Override
	public double pidGet() {
		return this.encoder.getRate();
	}
}
