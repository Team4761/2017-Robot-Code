package org.robockets.steamworks.ballintake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeBalls extends CommandGroup {

    public IntakeBalls() {
        addParallel(new SpinBallIntakeRollers(-0.75));
    }
}
