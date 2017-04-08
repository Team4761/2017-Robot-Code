package org.robockets.steamworks.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

import org.robockets.steamworks.TurnType;
import org.robockets.steamworks.camera.SetVisionEnabled;
import org.robockets.steamworks.drivetrain.DriveStraight;
import org.robockets.steamworks.drivetrain.Turn;

/**
 *
 * @author Jake Backer
 */
public class EasyAuto extends CommandGroup {

	private double turnAngle = 0;
	private double turnSpeed = 0;
	private double forwardDistance1 = 0;
	private double forwardDistance2 = 0;
	private double forwardDistance3 = 0;

	private double forwardSpeed1 = 0;
	private double forwardSpeed2 = 0;
	private double forwardSpeed3 = 0;

	private double visionSpeed = 0;

	/**
	 * @param startingPos Robot's starting position (1, 2, or 3)
	 */
	public EasyAuto(int startingPos) {

		addSequential(new SetVisionEnabled(true));
		if (startingPos == 1) {
			forwardSpeed1 = 40;
			forwardSpeed3 = 24;
			turnAngle = 64;
			turnSpeed = 110;
			visionSpeed = 90;
			forwardDistance1 = 89;
			forwardDistance2 = 0;
			forwardDistance3 = 37;
		} else if (startingPos == 3) {
			forwardSpeed1 = 40;
			forwardSpeed3 = 24;
			turnAngle = -62;
			turnSpeed = -110;
			visionSpeed = -90;
			forwardDistance1 = 86;
			forwardDistance2 = 0;
			forwardDistance3 = 37;
		} else {
			forwardSpeed1 = 30;
			forwardDistance1 = 80;
		}

		addSequential(new DriveStraight(forwardSpeed1, forwardDistance1));
		addSequential(new WaitCommand(0.1));
		if(turnAngle != 0 && (forwardDistance2+forwardDistance3) != 0) {
			addSequential(new Turn(TurnType.RELATIVE, turnAngle, turnSpeed));
			addSequential(new WaitCommand(1));
			if (forwardDistance2 != 0) {
				addSequential(new DriveStraight(24, forwardDistance2));
				addSequential(new WaitCommand(1));
			}
			//addSequential(new Turn(TurnType.CAMERA, visionSpeed));
			addSequential(new WaitCommand(0.1));
			addSequential(new DriveStraight(forwardSpeed3, forwardDistance3));
		}

		addSequential(new SetVisionEnabled(false));
	}
}
