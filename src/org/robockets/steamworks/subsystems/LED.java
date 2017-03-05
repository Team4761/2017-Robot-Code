package org.robockets.steamworks.subsystems;

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
    	System.out.println("Writing 50");
    }
}

