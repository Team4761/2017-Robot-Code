package org.robockets.steamworks.gearintake;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.commons.RelativeDirection;

/**
 * This will spin the gear intake wheels out constantly and
 * move the arm down until it hits the limit switch
 * @author Jake Backer
 */
public class DeliverGear extends CommandGroup {

	public DeliverGear() {
		addParallel(new MoveGearIntakeArm(RelativeDirection.ZAxis.DOWN, 0.75));
		addParallel(new SpinGearIntake(RelativeDirection.Malone.OUT, 0.75));
	}
}
