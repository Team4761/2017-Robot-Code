package org.robockets.steamworks.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.commands.MoveElevator;
import org.robockets.commons.RelativeDirection;

/**
 * @author Jake Backer
 */
public class Shoot extends CommandGroup {

    public Shoot(boolean usePID) {
    	if (!usePID) {
    		addParallel(new SpinSpinners());

        	addSequential(new WaitCommand(3));

        	addParallel(new MoveElevator(RelativeDirection.ZAxis.UP, 0.9));

    	} else {
    		addParallel(new ShootWithPID(60));
    		addSequential(new WaitUntilSpeed());
    		addParallel(new MoveElevator(RelativeDirection.ZAxis.UP, 0.9));
    	}
    }
    
    protected void initialize() {
    	Robot.ledSubsystem.cylon(1);
    }
    
    protected void end() {
    	Robot.ledSubsystem.cylon(56);
    }
}
