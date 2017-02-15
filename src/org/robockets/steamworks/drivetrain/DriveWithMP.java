package org.robockets.steamworks.drivetrain;

import org.robockets.steamworks.LinearSetpointGenerator;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Motion profiled driving
 * @author Brian Shin
 */
public class DriveWithMP extends Command {
	
	private LinearSetpointGenerator mp;
	private double targetPosition;
	private double velocity;

	/**
	 * Method for driving with motion profiling
	 * @param targetPosition Distance desired, in inches, and relative
	 * @param velocity Inches per second
	 */
    public DriveWithMP(double targetPosition, double velocity) {
        requires(Robot.drivetrain);
        this.targetPosition = targetPosition;
        this.velocity = velocity;
    }

    protected void initialize() {
    	mp = new LinearSetpointGenerator(targetPosition , velocity, RobotMap.leftEncoder.getDistance());
    	Robot.drivetrain.leftPodPID.enable();
    	Robot.drivetrain.rightPodPID.enable();
    }

    protected void execute() {
    	double setpoint = mp.next();
    	Robot.drivetrain.setDistance(setpoint);
    }

    protected boolean isFinished() {
		return !mp.hasNext();
    }

    protected void end() {
    	Robot.drivetrain.stop();
    	Robot.drivetrain.disableEncoderPID();
    }

    protected void interrupted() {
    	end();
    }
}
