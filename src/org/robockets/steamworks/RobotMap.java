package org.robockets.steamworks;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static TalonSRX rollerSpeedController = new TalonSRX(PortNumbers.SHOOTER_ROLLER_SC_PORT);

    public static Encoder rollerEncoder = new Encoder(PortNumbers.SHOOTER_ROLLER_ENCODER_PORT_ONE, PortNumbers.SHOOTER_ROLLER_ENCODER_PORT_TWO);

    public static TalonSRX conveyorSpeedControllerOne = new TalonSRX(PortNumbers.SHOOTER_CONVEYOR_SC_ONE);
    public static TalonSRX conveyorSpeedControllerTwo = new TalonSRX(PortNumbers.SHOOTER_CONVEYOR_SC_TWO);
    
    public static TalonSRX ballIntakeRoller = new TalonSRX(PortNumbers.BALL_INTAKE_ROLLER_SC_PORT);

    public static ADXRS450_Gyro gyro = new ADXRS450_Gyro(PortNumbers.gyroPort);

    // TODO: Add breakbeam sensor

    public static Talon frontLeftSpeedController = new Talon(PortNumbers.DRIVETRAIN_FRONT_LEFT_SC_PORT);
    public static Talon frontRightSpeedController = new Talon(PortNumbers.DRIVETRAIN_FRONT_RIGHT_SC_PORT);
    public static Talon backLeftSpeedController = new Talon(PortNumbers.DRIVETRAIN_BACK_LEFT_SC_PORT);
    public static Talon backRightSpeedController = new Talon(PortNumbers.DRIVETRAIN_BACK_RIGHT_SC_PORT);

    public static Encoder leftEncoder = new Encoder(PortNumbers.DRIVETRAIN_LEFT_ENCODER_PORT_ONE, PortNumbers.DRIVETRAIN_LEFT_ENCODER_PORT_TWO);
    public static Encoder rightEncoder = new Encoder(PortNumbers.DRIVETRAIN_RIGHT_ENCODER_PORT_ONE, PortNumbers.DRIVETRAIN_RIGHT_ENCODER_PORT_TWO);

    public static RobotDrive robotDrive = new RobotDrive(frontLeftSpeedController, backLeftSpeedController, frontRightSpeedController, backRightSpeedController);
}
