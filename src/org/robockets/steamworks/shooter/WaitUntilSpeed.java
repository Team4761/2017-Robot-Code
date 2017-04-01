package org.robockets.steamworks.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 *
 */
public class WaitUntilSpeed extends Command {

    public WaitUntilSpeed() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        final double ABSOLUTE_TOLERANCE = 1.0;

        boolean isOnTarget = Math.abs((Robot.shooter.shooterPIDController.getSetpoint() - RobotMap.rollerEncoderCounter.getRate())) <= ABSOLUTE_TOLERANCE;

        SmartDashboard.putBoolean("Is shooter PID on target?", isOnTarget);

        return isOnTarget;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
