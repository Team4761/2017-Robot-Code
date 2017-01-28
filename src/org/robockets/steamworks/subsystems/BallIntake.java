package org.robockets.steamworks.subsystems;

import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Rollers for taking in balls.
 * @author Simon Andrews
 */
public class BallIntake extends Subsystem {
	private boolean isEnabled = false;
	
    public void initDefaultCommand() {}
    
    public void spinRollers(double speed) {
    	if(isEnabled == false) {
    		RobotMap.ballIntakeRoller.set(speed);
    		isEnabled = true;
    	}
    }
    
    public void stopRollers() {
    	if(isEnabled == true) {
    		RobotMap.ballIntakeRoller.stopMotor();
    		isEnabled = false;
    	}
    }
    
    public boolean isEnabled() {
    	return isEnabled;
    }
}

