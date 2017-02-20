package org.robockets.steamworks.commands;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class KillEverything extends Command {

    public KillEverything() {
		requires(Robot.ballIntake);
		requires(Robot.elevator);
		requires(Robot.conveyor);
		requires(Robot.climber);
		requires(Robot.drivetrain);
		requires(Robot.shooter);
		requires(Robot.gearIntake);
		requires(Robot.intakeFlap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.ballIntake.stopRollers();
		Robot.elevator.stop();
		Robot.conveyor.stop();
		Robot.climber.stop();
		Robot.drivetrain.stop();
		Robot.shooter.stop();
		//Robot.intakeFlap.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
