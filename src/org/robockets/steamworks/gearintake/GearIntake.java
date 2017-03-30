package org.robockets.steamworks.gearintake;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class GearIntake extends Subsystem {

	public void initDefaultCommand() {

	}

	public void spinIntakeWheels(RelativeDirection.Malone direction, double speed) {
		if (direction == RelativeDirection.Malone.IN) {
			RobotMap.gearIntakeWheels.set(speed);
		} else {
			RobotMap.gearIntakeWheels.set(-speed);
		}
	}

	public void moveGearIntakeArm(RelativeDirection.ZAxis direction, double speed) {
		if (direction == RelativeDirection.ZAxis.UP) {
			RobotMap.gearIntakeWheels.set(speed);
		} else {
			RobotMap.gearIntakeWheels.set(-speed);
		}
	}


	public boolean isGearInRobot() {
		return RobotMap.gearInputBreakbeamSensor.get();
		//return false;
	}

	public void periodicSmartDashboard() {
		SmartDashboard.putBoolean("Gear Intake Indicator", isGearInRobot());
	}
}

