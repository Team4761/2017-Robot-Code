package org.robockets.steamworks.climber;

import org.robockets.steamworks.PortNumbers;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem{

	public static final double STALLING_THRESHOLD = 12; // The stalling current of the subsystem motor.
	
	/**
	 * Read the current that the motor of the climber is using.
	 * @return current		The current of the motor.	
	 */
	public double readCurrent() {
		return RobotMap.powerDistPanel.getCurrent(PortNumbers.CLIMBER_PDP_PORT);
	}
	
	/**
	 * Set the motor speed.
	 * @param speed 	A double (0-1) describing the speed to set the motor at. 
	 */
	public void setMotor(double speed) {
		RobotMap.climberMotor.set(speed);
	}
	
	public void initDefaultCommand() {

	}
}