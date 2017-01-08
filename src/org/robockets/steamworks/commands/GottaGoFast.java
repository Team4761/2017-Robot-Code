package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class GottaGoFast extends Command {

    double translate;
    double rotate;

    public GottaGoFast() {
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        translate = 0;
        rotate = 0;
    }

    protected void execute() {
        translate = OI.joystick.getRawAxis(1);
        rotate = OI.joystick.getRawAxis(4);

        Robot.drivetrain.driveArcade(translate, rotate);
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
