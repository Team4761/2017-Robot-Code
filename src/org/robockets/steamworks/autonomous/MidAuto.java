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

	public MidAuto(int startingPos) {

		int angleMultiplier;

		if (startingPos == 1) {
			angleMultiplier = 1;
		} else if (startingPos == 3) {
			angleMultiplier = -1;
		} else {
			angleMultiplier = 0;
		}

		int horizontalDirectionMultiplier;

		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			horizontalDirectionMultiplier = -1;
		} else {
			horizontalDirectionMultiplier = 1;
		}

		// FIXME: Values will be changed
		addSequential(new DriveWithMP(60, 20));
		addSequential(new Turn(TurnType.RELATIVE, 45* angleMultiplier, 60));
		// Align
		addSequential(new WaitForGearOut()); // This needs to enable lights
		addSequential(new DriveWithMP(-20, -10));
		// Turn to boiler
		addSequential(new Turn(TurnType.RELATIVE, 90 * horizontalDirectionMultiplier, 60));
		addSequential(new DriveWithMP(55, 20));
		// Align
		addSequential(new Shoot(true));
	}
}
