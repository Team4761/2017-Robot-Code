package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.drivetrain.DriveWithMP;
import org.robockets.steamworks.drivetrain.Turn;

/**
 *
 * @author Jake Backer
 */
public class EasyAuto extends CommandGroup {

	private double angleMultiplier;

	/**
	 * @param startingPos Robot's starting position (1, 2, or 3)
	 */
	public EasyAuto(int startingPos) {

		if (startingPos == 1) {
			angleMultiplier = 1;
		} else if (startingPos == 3) {
			angleMultiplier = -1;
		} else {
			angleMultiplier = 0;
		}

		// FIXME: Values will be changed
		addSequential(new DriveWithMP(60, 20));
		addSequential(new Turn(TurnType.RELATIVE, 45*angleMultiplier));
	}
}
