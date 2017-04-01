package org.robockets.steamworks.climber;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;
import org.robockets.commons.RelativeDirection;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem{

	public double STALLING_THRESHOLD_LEFT = 65; // The stalling current of the subsystem motor.
	public double STALLING_THRESHOLD_RIGHT = 70; // The stalling current of the subsystem motor.

	public void initDefaultCommand() {
	}

	/**
	 * Read the current that the motor of the climber is using.
	 * @return current The current of the motor.
	 */
	public double readCurrent(RelativeDirection.XAxis direction) {
		
		double leftCurrent = RobotMap.powerDistPanel.getCurrent(RobotMap.climberPdpPortLeft);
		
		double rightCurrent = RobotMap.powerDistPanel.getCurrent(RobotMap.climberPdpPortRight);
		
		SmartDashboard.putNumber("Current LEFT", leftCurrent);
		SmartDashboard.putNumber("Current RIGHT", rightCurrent);

		if (direction == RelativeDirection.XAxis.LEFT) {
			return Math.abs(RobotMap.powerDistPanel.getCurrent(RobotMap.climberPdpPortLeft));
		} else {
			return Math.abs(RobotMap.powerDistPanel.getCurrent(RobotMap.climberPdpPortRight));
		}
	}
	
	/**
	 * Set the motor speed.
	 * @param speed A double (0-1) describing the speed to set the motor at.
	 */
	public void setMotor(double speed) {
		RobotMap.climberSpeedController.set(speed);
		RobotMap.climberSpeedController2.set(-speed);
	}
	
	public void stop() {
		RobotMap.climberSpeedController.stopMotor();
		RobotMap.climberSpeedController2.stopMotor();
	}
	
	/**
	 * Method to run to initialize commands on the SmartDashboard.
	 */
	public void initSmartDashboard() {
		SmartDashboard.putData("Climb (speed 0.5, for 5 seconds)", new Climb(0.5, 5));
		SmartDashboard.putData("Climb (speed 0.5, forever)", new Climb(0.5, 0));
		SmartDashboard.putData("Climb (speed 0.5, until STALL)", new Climb(0.5, 1));
	}
	
	/**
	 * Method to run to update data on the SmartDashboard.
	 */
	
	public void periodicSmartDashboard() {
		SmartDashboard.putNumber("Climber Current", readCurrent(RelativeDirection.XAxis.LEFT));
	}
	
	public boolean isStalling() {
		double leftCurrent = RobotMap.powerDistPanel.getCurrent(RobotMap.climberPdpPortLeft);
		double rightCurrent = RobotMap.powerDistPanel.getCurrent(RobotMap.climberPdpPortRight);
		
		SmartDashboard.putNumber("current LEFT", leftCurrent);
		SmartDashboard.putNumber("current RIGHT", rightCurrent);

		return (leftCurrent  > Robot.climber.STALLING_THRESHOLD_LEFT)
			|| (rightCurrent > Robot.climber.STALLING_THRESHOLD_RIGHT);
	}
}