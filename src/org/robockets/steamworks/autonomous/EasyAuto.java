package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.camera.CVConstants;
import org.robockets.steamworks.drivetrain.DriveStraight;
import org.robockets.steamworks.drivetrain.Turn;

/**
 *
 * @author Jake Backer
 */
public class EasyAuto extends CommandGroup {

	private double turnAngle = 0;
	private double forwardDistance1 = 0;
	private double forwardDistance2 = 0;

	/**
	 * @param startingPos Robot's starting position (1, 2, or 3)
	 */
	public EasyAuto(int startingPos) {

		if (startingPos == 1) {
			turnAngle = 55; // FIXME: Change this
			forwardDistance1 = 75;
			forwardDistance2 = 75; // Unused
		} else if (startingPos == 3) {
			turnAngle = -55;
			forwardDistance1 = 75;
			forwardDistance2 = 75;
		} else {
			forwardDistance1 = 80;
		}

		// FIXME: Values will be changed
		addSequential(new DriveStraight(24, forwardDistance1));
		addSequential(new WaitCommand(1));
		if(turnAngle != 0 && forwardDistance2 != 0) {
			addSequential(new Turn(TurnType.RELATIVE, turnAngle, -60));
			addSequential(new WaitCommand(1));
			//addSequential(new Turn(TurnType.RELATIVE, CVConstants.getOffset()));
			addSequential(new DriveStraight(24, forwardDistance2)); // FIXME: Change this
		}
	}
}
