package org.robockets.steamworks.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Jake Backer
 */
public class Shoot extends CommandGroup {

    public Shoot(int time) {
        addParallel(new SpinSpinners());
        try {
            wait(100); // This will be changed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addSequential(new MoveConveyor(2)); // This will also be changed
    }

    public Shoot() {
        addParallel(new SpinSpinners());
        try {
            wait(100); // This will be changed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addSequential(new MoveConveyor()); // This will also be changed
    }
}
