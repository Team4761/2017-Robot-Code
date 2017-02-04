package org.robockets.steamworks.drivetrain;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveWithDDP extends CommandGroup {

    public DriveWithDDP(double distance, double velocity) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addParallel(new SpinDrivePod(Robot.drivetrain.leftPodPID, Robot.leftDDP, distance, velocity));
    	//addParallel(new SpinDrivePod(Robot.drivetrain.rightPodPID, Robot.rightDDP, distance, velocity));
    }
    
    public void initialize() {
		RobotMap.leftEncoder.reset();
		RobotMap.rightEncoder.reset();
    }
}
