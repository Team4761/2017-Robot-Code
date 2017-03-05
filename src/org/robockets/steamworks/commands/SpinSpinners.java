package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class SpinSpinners extends Command {

    private double time;
    private boolean isForever;

    public SpinSpinners() {
        requires(Robot.shooter);
        isForever = true;
    }

    public SpinSpinners(double time) {
        requires(Robot.shooter);
        this.time = time;
        isForever = false;
    }

    protected void initialize() {
        if (!isForever) {
            setTimeout(time);
        }
    }

    protected void execute() {
        Robot.shooter.spinUp(1);
    }

    protected boolean isFinished() {
        return !isForever && isTimedOut();
    }

    protected void end() {
        Robot.shooter.stop();
    }

    protected void interrupted() {
        end();
    }
}
