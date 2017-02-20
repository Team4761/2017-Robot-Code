package org.robockets.steamworks.commands;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import org.robockets.commons.RelativeDirection;

/**
 * @author Jake Backer
 */
public class Shoot extends CommandGroup {

    public Shoot(int time) {
        addParallel(new SpinSpinners());

        addSequential(new WaitCommand(0.1));
        
        addSequential(new MoveConveyor(RelativeDirection.YAxis.FORWARD)); // This will also be changed

        addSequential(new WaitCommand(2));

        addParallel(new MoveElevator(RelativeDirection.ZAxis.UP, 1));

        addSequential(new WaitCommand(2)); // Time for it to shoot

        addSequential(new KillShooter());
    }

    public Shoot() {
        addParallel(new SpinSpinners());
        try {
            wait(100); // This will be changed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addSequential(new MoveConveyor(RelativeDirection.YAxis.FORWARD)); // This will also be changed
    }
    
    protected void initialize() {
    	Robot.ledSubsystem.cylon(1);
    }
    
    protected void end() {
    	Robot.ledSubsystem.cylon(56);
    }
}
