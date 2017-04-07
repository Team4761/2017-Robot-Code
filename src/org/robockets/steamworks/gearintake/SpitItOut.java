package org.robockets.steamworks.gearintake;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;

/**
 * Shoot the gear out of the Robot
 * @author Jake Backer
 */
public class SpitItOut extends CommandGroup {

	public SpitItOut() {
		requires(Robot.gearIntake);
		addParallel(new MoveGearIntakeArm(RelativeDirection.ZAxis.DOWN, 0.6, true, 1.5));
		addParallel(new SpinGearIntake(RelativeDirection.Malone.OUT, 0.3, 1.5));
	}
}
