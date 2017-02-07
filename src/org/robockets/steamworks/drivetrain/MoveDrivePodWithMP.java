package org.robockets.steamworks.drivetrain;

import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveDrivePodWithMP extends Command {

	LinearSetpointGenerator mp;
	
    public MoveDrivePodWithMP() {
    	requires(Robot.drivetrain);
    	System.out.println("new command constructed");
    }

    protected void initialize() {
    	System.out.println("new command initialized()");
    	mp = new LinearSetpointGenerator(24 /* inches */, 8 /* inches per second */, RobotMap.leftEncoder.getDistance());
    	Robot.drivetrain.leftPodPID.enable();
    	mp.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double setpoint = mp.getSetpoint();
    	SmartDashboard.putNumber("mp out", setpoint);
    	Robot.drivetrain.leftPodPID.setSetpoint(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return mp.isInPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.leftPodPID.disable();
    	System.out.println("a command has reached completion");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
