package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * Command to drive straight with the help of a gyro
 *
 */
public class DriveStraight extends Command {
    private RelativeDirection.YAxis direction;
    private double speed;

    /**
     * Setup drive
     * @param direction Forwards or Backwards
     * @param speed Speed multiplier
     */
    public DriveStraight(RelativeDirection.YAxis direction, double speed) {
        this.direction = direction;
        this.speed = speed;
    }

    protected void initialize() {}

    protected void execute() {
        if (direction == RelativeDirection.YAxis.BACKWARD) {
        	speed = -speed;        
        }
        
        Robot.drivetrain.driveTank(speed, speed);
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
