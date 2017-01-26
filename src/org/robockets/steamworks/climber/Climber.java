package org.robockets.steamworks.climber;

import org.robockets.steamworks.PortNumbers;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem{

	public double STALLING_THRESHOLD = 12; // The stalling current of the subsystem motor.
	
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
	
	/**
	 * Method to run to initialize commands on the SmartDashboard.
	 * @param debug		Whether to show a complete set of options of commands or not.
	 */
	public void initSmartDashboard(boolean debug) {
		// Debug set.
		if (debug == true) {
			SmartDashboard.putData("Climb (speed 0.5, for 5 seconds)", new Climb(0.5, 1));
			SmartDashboard.putData("Climb (speed 0.5, forever)", new Climb(0.5, 0));
			SmartDashboard.putNumber("STALLING_THRESHOLD", STALLING_THRESHOLD);
		}
		SmartDashboard.putData("Climb (speed 0.5, until STALL)", new Climb(0.5, 1));
	}
	
	/**
	 * Method to run to update data on the SmartDashboard.
	 * @param debug		True to show current, other data, and allow editing of THRESHOLD.
	 */
	
	public void periodicSmartDashboard(boolean debug) {
		if (debug) {
			SmartDashboard.putNumber("Climber Current", readCurrent());
			STALLING_THRESHOLD = SmartDashboard.getNumber("STALLING_THRESHOLD", STALLING_THRESHOLD);
		}
		SmartDashboard.putBoolean("Climber STALLING", readCurrent() > STALLING_THRESHOLD);
	}
}