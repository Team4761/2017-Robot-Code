package org.robockets.steamworks.subsystems;



import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LED extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void cylon (int value) {
    	RobotMap.arduino.write(8,value);
    	System.out.println("Writing 50");
    }
}

