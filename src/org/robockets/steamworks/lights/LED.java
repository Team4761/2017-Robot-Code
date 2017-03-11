package org.robockets.steamworks.lights;

import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LED extends Subsystem {

    public void initDefaultCommand() {
    }
    public void cylon (int value) {
    	RobotMap.arduino.write(8,value);
    }
}

