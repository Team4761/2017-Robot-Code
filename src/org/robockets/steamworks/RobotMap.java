package org.robockets.steamworks;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static TalonSRX frontLeftSpeedController = new TalonSRX(PortNumbers.DRIVETRAIN_FRONT_LEFT_SC_PORT);
    public static TalonSRX frontRightSpeedController = new TalonSRX(PortNumbers.DRIVETRAIN_FRONT_RIGHT_SC_PORT);
    public static TalonSRX backLeftSpeedController = new TalonSRX(PortNumbers.DRIVETRAIN_BACK_LEFT_SC_PORT);
    public static TalonSRX backRightSpeedController = new TalonSRX(PortNumbers.DRIVETRAIN_BACK_RIGHT_SC_PORT);

    public static Encoder frontLeftEncoder = new Encoder(PortNumbers.DRIVETRAIN_FRONT_LEFT_ENCODER_PORT_ONE, PortNumbers.DRIVETRAIN_FRONT_LEFT_ENCODER_PORT_TWO);
    public static Encoder backLeftEncoder = new Encoder(PortNumbers.DRIVETRAIN_BACK_LEFT_ENCODER_PORT_ONE, PortNumbers.DRIVETRAIN_BACK_LEFT_ENCODER_PORT_TWO);
    public static Encoder frontRightEncoder = new Encoder(PortNumbers.DRIVETRAIN_FRONT_RIGHT_ENCODER_PORT_ONE, PortNumbers.DRIVETRAIN_FRONT_RIGHT_ENCODER_PORT_TWO);
    public static Encoder backRightEncoder = new Encoder(PortNumbers.DRIVETRAIN_BACK_RIGHT_ENCODER_PORT_ONE, PortNumbers.DRIVETRAIN_BACK_RIGHT_ENCODER_PORT_TWO);

    public static RobotDrive robotDrive = new RobotDrive(frontLeftSpeedController, backLeftSpeedController, frontRightSpeedController, backRightSpeedController);

    public static SerialPort serial = new SerialPort(9600, SerialPort.Port.kUSB);
}
