package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.commands.Shoot;
import org.robockets.steamworks.commands.WaitForGearOut;
import org.robockets.steamworks.drivetrain.DriveWithMP;
import org.robockets.steamworks.drivetrain.Turn;

/**
 * @author Jake Backer
 */
public class MaxAuto extends CommandGroup {

	private int angleMultiplier;

	public MaxAuto(int startingPos) {

		if (startingPos == 1) {
			angleMultiplier = 1;
		} else if (startingPos == 3) {
			angleMultiplier = -1;
		} else {
			angleMultiplier = 0;
		}

		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			// Changes variable for below
		} else {

		}

		// FIXME: Values will be changed
		addSequential(new DriveWithMP(60, 20));
		addSequential(new Turn(TurnType.RELATIVE, 45*angleMultiplier));
		addSequential(new WaitForGearOut());
		addSequential(new DriveWithMP(-20, -10));
		// Turn to hopper (needs alliance stuff)
		addSequential(new DriveWithMP(60, 20));
		addSequential(new WaitCommand(5));
		addSequential(new DriveWithMP(-30, -15));
		// Turn to boiler (needs alliance stuff)
		addSequential(new DriveWithMP(60, 20));
		// Align
		addSequential(new Shoot());
	}
}
