package org.robockets.steamworks.pidoutput;

import edu.wpi.first.wpilibj.PIDOutput;

import org.robockets.steamworks.Robot;

/**
 *
 */
public class GyroPIDOutput implements PIDOutput {

    public GyroPIDOutput() {}
    
    @Override
    public void pidWrite(double output) {
    	//Robot.drivetrain.driveArcade(0, output);
    }
}
