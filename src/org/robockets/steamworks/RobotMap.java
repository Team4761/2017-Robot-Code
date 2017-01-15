package org.robockets.steamworks;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    private static TalonSRX drivetrainLeftSpeedController = new TalonSRX(PortNumbers.DRIVETRAIN_LEFT_SC_PORT);
    private static TalonSRX rightSideSpeedController = new TalonSRX(PortNumbers.DRIVETRAIN_RIGHT_SC_PORT);

    public static RobotDrive robotDrive = new RobotDrive(drivetrainLeftSpeedController, rightSideSpeedController);
}
