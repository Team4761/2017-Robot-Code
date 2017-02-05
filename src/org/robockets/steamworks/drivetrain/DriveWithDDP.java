package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveWithDDP extends CommandGroup {

    public DriveWithDDP(double distance, double velocity) {
    	addParallel(new SpinDrivePod(Robot.drivetrain.leftPodPID, Robot.leftDDP, distance, velocity));
    	//addParallel(new SpinDrivePod(Robot.drivetrain.rightPodPID, Robot.rightDDP, distance, velocity));
    }
    
    public void initialize() {
		/*RobotMap.leftEncoder.reset();
		RobotMap.rightEncoder.reset();*/
    }
}
