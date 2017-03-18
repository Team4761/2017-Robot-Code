package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.commands.WaitForGearOut;
import org.robockets.steamworks.drivetrain.DriveStraight;
import org.robockets.steamworks.drivetrain.DriveWithMP;
import org.robockets.steamworks.drivetrain.Turn;
import org.robockets.steamworks.shooter.Shoot;

/**
 * @author Jake Backer
 */
public class MidAuto extends CommandGroup {

	private double turnAngle = 0;
	private double forwardDistance1 = 0;
	private double forwardDistance2 = 0;

	public MidAuto(int startingPos) {

		int angleMultiplier;

		if (startingPos == 1) {
			angleMultiplier = 1;
		} else if (startingPos == 3) {
			angleMultiplier = -1;
		} else {
			angleMultiplier = 0;
			forwardDistance1 = 80;
		}

		int horizontalDirectionMultiplier;

		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			horizontalDirectionMultiplier = -1;
		} else {
			horizontalDirectionMultiplier = 1;
		}

		// FIXME: Values will be changed
		addSequential(new DriveStraight(24, forwardDistance1));
		// Align
		addSequential(new WaitForGearOut());
		addSequential(new DriveStraight(24, -15)); // Make this a variable

		// Turn to boiler
		addSequential(new Turn(TurnType.RELATIVE, 90 * horizontalDirectionMultiplier, 60));
		addSequential(new DriveWithMP(55, 20));
		// Align
		addSequential(new Shoot(true));
	}
}
