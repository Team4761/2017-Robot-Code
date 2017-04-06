package org.robockets.steamworks.autonomous;

import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;
import org.robockets.steamworks.camera.CVConstants;
import org.robockets.steamworks.camera.SetVisionEnabled;
import org.robockets.steamworks.drivetrain.Drivetrain;

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
    }

    protected void execute() {
    	if(RobotMap.powerDistPanel.getCurrent(5) > 4) {
    		leftLsg = new LinearSetpointGenerator(-24, -12, RobotMap.leftEncoder.getDistance());
    		rightLsg = new LinearSetpointGenerator(-24, -12, RobotMap.rightEncoder.getDistance());
    	}
    	
    	if(!leftLsg.hasNext() || !rightLsg.hasNext()) {
    		double angle;
    		do {
    			angle = CVConstants.getOffset();
    		} while(angle > CVConstants.LOGITECH_C270_FOV / 2);
    		double theta = Math.toRadians(angle);
    		double distance = Math.abs(Drivetrain.CENTERPOINT_TO_WHEEL * theta);
    		
    		double leftVelocity = (theta < 0) ? 12 : -12;
    		leftLsg = new LinearSetpointGenerator(distance, leftVelocity, RobotMap.leftEncoder.getDistance());
    		rightLsg = new LinearSetpointGenerator(-distance,-leftVelocity, RobotMap.rightEncoder.getDistance());
    	}
    	
    	Robot.drivetrain.leftPodPID.setSetpoint(leftLsg.next());
    	Robot.drivetrain.rightPodPID.setSetpoint(rightLsg.next());
    	
    	Robot.drivetrain.leftPodPID.enable();
    	Robot.drivetrain.rightPodPID.enable();
    }

    protected boolean isFinished() {
        return Robot.drivetrain.encodersOnTarget();
    }

    protected void end() {
    	Robot.drivetrain.leftPodPID.disable();
    	Robot.drivetrain.rightPodPID.disable();
    	new SetVisionEnabled(false).start();
    }

    protected void interrupted() {
    	end();
    }
}
