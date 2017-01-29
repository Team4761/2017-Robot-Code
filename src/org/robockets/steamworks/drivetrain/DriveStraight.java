package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * General drive command
 * @author Jake Backer
 */
public class DriveStraight extends Command {
    private int offset;
    private RelativeDirection.YAxis direction;
    private double speed;

    /**
     * Setup drive
     * @param offset How much to offset rotation between -1 and 1
     * @param direction Forwards or Backwards
     * @param speed Speed multiplier
     */
    public DriveStraight(int offset, RelativeDirection.YAxis direction, double speed) {
        this.offset = offset;
        this.direction = direction;
        this.speed = speed;
    }

    protected void initialize() {

    }

    protected void execute() {
        if (direction == RelativeDirection.YAxis.FORWARD) {
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
