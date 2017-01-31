package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.commons.RelativeDirection;

/**
 * @author Jake Backer
 */
public class Shoot extends CommandGroup {

    public Shoot() {
        addParallel(new SpinSpinners());

        addSequential(new Wait(2)); // This will be changed

        addParallel(new MoveConveyor(RelativeDirection.YAxis.FORWARD)); // This will also be changed
        addParallel(new MoveElevator(RelativeDirection.ZAxis.UP));

        addSequential(new Wait(2)); // Time for it to shoot

        addSequential(new KillShooter());
    }

    // Testing
    protected void end() {
        System.out.println("ShootEnded!");
    }
}
