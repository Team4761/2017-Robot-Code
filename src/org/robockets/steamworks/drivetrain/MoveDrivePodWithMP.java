package org.robockets.steamworks.drivetrain;

import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveDrivePodWithMP extends Command {

	private LinearSetpointGenerator mp;
	private double targetPosition;
	private double velocity;

	/**
	 * Method for driving with motion profiling
	 * @param targetPosition Distance desired, in inches, and relative. Negative for backwards
	 * @param velocity Inches per second, negative for backwards
	 */
	public MoveDrivePodWithMP(double targetPosition, double velocity) {
		requires(Robot.drivetrain);
		this.targetPosition = targetPosition;
		this.velocity = velocity;
		System.out.println("new command constructed");
	}

    protected void initialize() {
    	System.out.println("new command initialized()");
    	mp = new LinearSetpointGenerator(targetPosition , velocity, RobotMap.leftEncoder.getDistance());
    	Robot.drivetrain.leftPodPID.enable();
	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double setpoint = mp.next();
		SmartDashboard.putNumber("mp out", setpoint);
    	Robot.drivetrain.leftPodPID.setSetpoint(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		System.out.println(!mp.hasNext());
		return !mp.hasNext();
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
