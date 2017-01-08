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

    public static TalonSRX frontLeft = new TalonSRX(PortNumbers.DRIVETRAIN_FRONT_LEFT_PORT);
    public static TalonSRX frontRight = new TalonSRX(PortNumbers.DRIVETRAIN_FRONT_RIGHT_PORT);
    public static TalonSRX backLeft = new TalonSRX(PortNumbers.DRIVETRAIN_BACK_LEFT_PORT);
    public static TalonSRX backRight = new TalonSRX(PortNumbers.DRIVETRAIN_BACK_RIGHT_PORT);

    public static RobotDrive robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
}
