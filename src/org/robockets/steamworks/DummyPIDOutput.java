package org.robockets.steamworks;

import edu.wpi.first.wpilibj.PIDOutput;

public class DummyPIDOutput implements PIDOutput {

    private double value = 0;

    @Override
    public void pidWrite(double output) {
        this.value = output;
    }

    public double getValue() {
        return this.value;
    }
}
