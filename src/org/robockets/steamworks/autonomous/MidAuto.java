package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.commands.Shoot;
import org.robockets.steamworks.commands.WaitForGearOut;
import org.robockets.steamworks.drivetrain.DriveWithMP;
import org.robockets.steamworks.drivetrain.Turn;

/**
 * @author Jake Backer
 */
public class MidAuto extends CommandGroup {

	private int angleMultiplier;

	public MidAuto(int startingPos) {

		if (startingPos == 1) {
			angleMultiplier = 1;
		} else if (startingPos == 3) {
			angleMultiplier = -1;
		} else {
			angleMultiplier = 0;
		}

		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			// Change some variable for below
		} else {

		}

		// FIXME: Values will be changed
		addSequential(new DriveWithMP(60, 20));
		addSequential(new Turn(TurnType.RELATIVE, 45*angleMultiplier));
		addSequential(new WaitForGearOut());
		addSequential(new DriveWithMP(-20, -10));
		// Turn to boiler (this need the Alliance stuff)
		addSequential(new DriveWithMP(55, 20));
		// Align
		addSequential(new Shoot());
	}
}
