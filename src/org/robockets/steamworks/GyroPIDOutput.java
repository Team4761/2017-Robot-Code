package org.robockets.steamworks;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 */
public class GyroPIDOutput implements PIDOutput {

    public GyroPIDOutput() {}
    
    @Override
    public void pidWrite(double output) {
    	Robot.drivetrain.driveArcade(0, output);
    }
}
