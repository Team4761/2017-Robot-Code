package org.robockets.steamworks.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class GearIntake extends Subsystem {

	public void initDefaultCommand() {

	}

	public boolean isGearInRobot() {
		return false;//RobotMap.gearInputBreakbeamSensor.get();
	}

	public void periodicSmartDashboard() {
		SmartDashboard.putBoolean("Gear Intake Indicator", isGearInRobot());
	}
}

