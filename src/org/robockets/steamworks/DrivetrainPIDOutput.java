package org.robockets.steamworks;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivetrainPIDOutput implements PIDOutput {

	private RobotDrive robotDrive;

	public DrivetrainPIDOutput(RobotDrive robotDrive) {
		this.robotDrive = robotDrive;
	}

	@Override
	public void pidWrite(double value) {
		SmartDashboard.putNumber("DrivetrainPIDOutput", value);
		robotDrive.tankDrive(value, value);
	}
}
