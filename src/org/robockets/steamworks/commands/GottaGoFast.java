package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.OI;
import org.robockets.steamworks.PortNumbers;
import org.robockets.steamworks.Robot;

/**
 * Controlled teleop drive command
 * @author Jake Backer
 */
public class GottaGoFast extends Command {

    double translate;
    double rotate;

    double speed;
    /**
     *
     * @param speed Speed multiplier
     */
    public GottaGoFast(double speed) {
        requires(Robot.drivetrain);
        this.speed = speed;
    }

    protected void initialize() {
        translate = 0;
        rotate = 0;
    }

    protected void execute() {
        translate = OI.joystick.getRawAxis(PortNumbers.JOYSTICK_TRANSLATE_AXIS);
        rotate = OI.joystick.getRawAxis(PortNumbers.JOYSTICK_ROTATE_AXIS);

        Robot.drivetrain.driveArcade(translate*speed, rotate*speed);
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
