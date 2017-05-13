package org.robockets.steamworks.mystery;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;

import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class FollowTrajectory extends Command {

	private File myFile;
	private Trajectory trajectory;
	private TankModifier tankModifier;
	private EncoderFollower leftEncoderFollower, rightEncoderFollower;
	
    public FollowTrajectory() {
    	myFile = new File("~/trajectory.csv");
    	trajectory = Pathfinder.readFromCSV(myFile);
    	tankModifier = new TankModifier(trajectory).modify(29.5);
    	
    	leftEncoderFollower = new EncoderFollower(tankModifier.getLeftTrajectory());
    	rightEncoderFollower = new EncoderFollower(tankModifier.getRightTrajectory());	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftEncoderFollower.configureEncoder(RobotMap.leftEncoder.get(), 360, 0.102);
    	rightEncoderFollower.configureEncoder(RobotMap.rightEncoder.get(), 360, 0.102);
    	
    	leftEncoderFollower.configurePIDVA(0.06, 0.01, 0.25, 1 / 3.658, 0);
    	rightEncoderFollower.configurePIDVA(-0.06, -0.01, -0.25, 1 / 3.658, 0);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double l = leftEncoderFollower.calculate(RobotMap.leftEncoder.get());
    	double r = rightEncoderFollower.calculate(RobotMap.rightEncoder.get());
    	
    	RobotMap.leftDrivepodSpeedController.set(l);
    	RobotMap.rightDrivepodSpeedController.set(r);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// TODO: Implement me
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.leftDrivepodSpeedController.set(0);
    	RobotMap.rightDrivepodSpeedController.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
