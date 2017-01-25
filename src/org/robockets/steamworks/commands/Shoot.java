package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.commons.RelativeDirection;

/**
 * @author Jake Backer
 */
public class Shoot extends CommandGroup {

    public Shoot() {
        addParallel(new SpinSpinners());
        try {
            wait(100); // This will be changed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addParallel(new MoveConveyor(2)); // This will also be changed
        addParallel(new MoveElevator(RelativeDirection.ZAxis.UP, 2));
        addSequential(new KillShooter());
    }
}
