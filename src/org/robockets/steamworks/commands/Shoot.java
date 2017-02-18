package org.robockets.steamworks.commands;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
        
        addSequential(new MoveConveyor(2)); // This will also be changed
    }
    
    protected void initialize() {
    	Robot.ledSubsystem.cylon(1);
    }
    
    protected void end() {
    	Robot.ledSubsystem.cylon(56);
    }
}
