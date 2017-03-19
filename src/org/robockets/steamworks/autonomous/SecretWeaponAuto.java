package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.commands.MoveElevator;
import org.robockets.steamworks.drivetrain.DriveArbitrary;
import org.robockets.steamworks.shooter.KillShooter;
import org.robockets.steamworks.shooter.ShootWithPID;
import org.robockets.steamworks.shooter.WaitUntilSpeed;

/**
 * Shoot THEN gear
 * This REQUIRES being in the position closest to the boiler
 * and at a very specific angle
 * @author Jake Backer
 */
public class SecretWeaponAuto extends CommandGroup {

	private int turnMultiplier = 1;

	public SecretWeaponAuto() {

		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
			turnMultiplier = -1;
		}

		addParallel(new ShootWithPID());
		addSequential(new WaitUntilSpeed());
		addSequential(new MoveElevator(RelativeDirection.ZAxis.UP, 0.6, 1.5)); // TODO: Change this to be with breakbeam later
		addSequential(new KillShooter());
		addSequential(new DriveArbitrary(0, 0, 0 ,0)); // FIXME: Change these values. This also needs to take into account the angleMultiplier
	}
}
