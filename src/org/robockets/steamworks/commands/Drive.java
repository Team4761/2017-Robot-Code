package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.XAxisRelativeDirection;
import org.robockets.commons.YAxisRelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * General drive command
 * @author Jake Backer
 */
public class Drive extends Command {

    int offset;
    YAxisRelativeDirection direction;
    double speed;

    /**
     * Setup drive
     * @param offset How much to offset rotation between -1 and 1
     * @param direction Forwards or Backwards
     * @param speed Speed multiplier
     */
    public Drive(int offset, YAxisRelativeDirection direction, double speed) {
        this.offset = offset;
        this.direction = direction;
        this.speed = speed;
    }

    protected void initialize() {

    }

    protected void execute() {
        if (direction == YAxisRelativeDirection.FORWARD) {
            Robot.drivetrain.driveArcade(0.5*speed, offset*0.5*speed); // 0.5 is the default speed. This is subject to change
        } else {
            Robot.drivetrain.driveArcade(-0.5*speed, offset*-0.5*speed);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.stop();
    }

    protected void interrupted() {
        end();
    }
}