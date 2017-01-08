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

    public static TalonSRX frontLeft = new TalonSRX(0);
    public static TalonSRX frontRight = new TalonSRX(1);
    public static TalonSRX backLeft = new TalonSRX(2);
    public static TalonSRX backRight = new TalonSRX(3);

    public static RobotDrive robotDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
}
