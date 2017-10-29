package org.robockets.steamworks.autonomous;

import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;
import org.robockets.steamworks.camera.SetVisionEnabled;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DeliverGearPlusPlus extends Command {
	private final double expectedInitialDistance;
	
	private LinearSetpointGenerator leftLsg, rightLsg;
	
    public DeliverGearPlusPlus(double expectedInitialDistance) {
    	requires(Robot.drivetrain);
    	this.expectedInitialDistance = expectedInitialDistance;
    }

    protected void initialize() {
    	new SetVisionEnabled(true).start();
    	leftLsg = new LinearSetpointGenerator(expectedInitialDistance, 24, RobotMap.leftEncoder.getDistance());
    	rightLsg = new LinearSetpointGenerator(expectedInitialDistance, 24, RobotMap.rightEncoder.getDistance());
		Robot.drivetrain.leftPodDistancePID.enable();
		Robot.drivetrain.rightPodDistancePID.enable();
    }

    // PDP ports: 12 & 3 for one side of the robot
	// 13 & 0 for the other side
    protected void execute() {
    	if(Robot.drivetrain.getAverageCurrent() > 6) {
    		leftLsg = new LinearSetpointGenerator(-24, -24, RobotMap.leftEncoder.getDistance());
    		rightLsg = new LinearSetpointGenerator(-24, -24, RobotMap.rightEncoder.getDistance());
    	}
    	
    	if(!leftLsg.hasNext() || !rightLsg.hasNext()) {
    		leftLsg = new LinearSetpointGenerator(24, 24, RobotMap.leftEncoder.getDistance());
    		rightLsg = new LinearSetpointGenerator(24, 24, RobotMap.rightEncoder.getDistance());
    	}
    	
    	Robot.drivetrain.leftPodDistancePID.setSetpoint(leftLsg.next());
    	Robot.drivetrain.rightPodDistancePID.setSetpoint(rightLsg.next());
    }

    protected boolean isFinished() {
        return Robot.drivetrain.distanceEncodersOnTarget();
    }

    protected void end() {
    	Robot.drivetrain.leftPodDistancePID.disable();
    	Robot.drivetrain.rightPodDistancePID.disable();
    	new SetVisionEnabled(false).start();
    }

    protected void interrupted() {
    	end();
    }
}
