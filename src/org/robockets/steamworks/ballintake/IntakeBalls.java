package org.robockets.steamworks.ballintake;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.commands.MoveConveyor;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeBalls extends CommandGroup {

    public IntakeBalls() {
        addParallel(new SpinBallIntakeRollers(-1));
        addParallel(new MoveConveyor(RelativeDirection.YAxis.FORWARD));
    }
}
