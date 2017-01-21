package org.robockets.steamworks.subsystems;

import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Rollers for taking in balls.
 */
public class BallIntake extends Subsystem {
	private boolean isEnabled = false;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void spinRollers(double speed) {
    	if(isEnabled == false) {
    		RobotMap.ballIntakeRoller.set(speed);
    	}
    }
    
    public void stopRollers() {
    	if(isEnabled == true) {
    		RobotMap.ballIntakeRoller.stopMotor();
    	}
    }
    
    public boolean isEnabled() {
    	return isEnabled;
    }
}

