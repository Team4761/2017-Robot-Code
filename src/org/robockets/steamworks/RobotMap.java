package org.robockets.steamworks;

import org.robockets.steamworks.drivetrain.DrivePodOutput;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	///////////
	/// PWM ///
	///////////
	
    public static Victor elevatorSpeedController = new Victor(0);

	public static Victor shooterRollerSpeedController = new Victor(1);
    /**
     * Speed controller for the motor that is used to climb up rope. This might
     * control two motors someday.
     */
    public static Victor climberSpeedController = new Victor(2);

	public static Victor gearIntakeWheels = new Victor(3);

    /**
     * Speed controller for right side of the robot.
     */
    public static Victor rightDrivepodSpeedController = new Victor(4);
    
    /**
     * Speed controller for the left side of the robot.
     */
    public static Victor leftDrivepodSpeedController = new Victor(5);
    
    public static Victor climberSpeedController2 = new Victor(6);

    public static Victor gearIntakeArm = new Victor(7);
  
    public static Servo leftIntakeFlapServo = new Servo(8);
    
    public static Servo rightIntakeFlapServo = new Servo(9);

	//////////////////
	/// Digital IO ///
	//////////////////
	
    public static DigitalInput gearInputBreakbeamSensor = new DigitalInput(0);
    
    public static DigitalInput elevatorBreakbeamSensor =  new DigitalInput(1);
	
	/**
	 * Encoder that goes on the shooter roller. For getting how fast the
	 * shooter roller is spinning.
	 */
    public static DigitalInput rollerEncoder = new DigitalInput(2);

	public static DigitalInput gearIntakeUpperLimitSwitch = new DigitalInput(3);

	public static DigitalInput gearIntakeLowerLimitSwitch = new DigitalInput(4);

	// 5

	/**
	 * Encoder that goes on the right drivepod. For getting how fast the right
	 * side of the robot is driving.
	 */
	public static Encoder rightEncoder = new Encoder(6, 7);
    
	/**
	 * Encoder that goes on the left drivepod. For getting how fast the left
	 * side of the robot is driving.
	 */
	public static Encoder leftEncoder = new Encoder(8, 9);

    ///////////
    /// SPI ///
    ///////////
    
    /**
     * The gyro. Needs to be mounted horizontally.
     */
    public static ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

    /////////////////////
    /// Miscellaneous ///
    /////////////////////
    
    public static I2C arduino = new I2C(Port.kOnboard, 8 );
    
    /**
     * {@link edu.wpi.first.wpilibj.RobotDrive RobotDrive} for controlling the
     * the four speed controllers on our two drivepods. Used in teleoperated
     * mode.
     */
    public static RobotDrive robotDrive = new RobotDrive(leftDrivepodSpeedController, rightDrivepodSpeedController);
    
    /**
     * {@link edu.wpi.first.wpilibj.PIDOutput PIDOutput} for controlling the
     * left drivepod.
     */
    public static DrivePodOutput leftDrivePodOutput = new DrivePodOutput(leftDrivepodSpeedController);
    
    /**
     * {@link edu.wpi.first.wpilibj.PIDOutput PIDOutput} for controlling the
     * right drivepod.
     */
    public static DrivePodOutput rightDrivePodOutput = new DrivePodOutput(rightDrivepodSpeedController);
    
    /**
     * The power distribution panel. Used for getting current to the climber
     * motor, letting us know when it is stalling.
     */
    public static PowerDistributionPanel powerDistPanel = new PowerDistributionPanel(); // Please note that this must be CAN id 0.
    
    /**
     * PDP port that the climber's motor is attached to.
     */
    public static final int climberPdpPortRight = 14;
    public static final int climberPdpPortLeft = 7;
    
    public static final double INTAKE_FLAP_FUEL_LEFT_POS = 1;
    public static final double INTAKE_FLAP_FUEL_RIGHT_POS = 0;
    public static final double INTAKE_FLAP_GEARS_LEFT_POS = 0.5;
    public static final double INTAKE_FLAP_GEARS_RIGHT_POS = 0.5;
    
    public static Counter rollerEncoderCounter = new Counter();

    public class SmartDashboardKey {
    	public static final String kCameraExposure = "Camera exposure";
    	public static final String kVideoFeedResolution = "Video feed resolution";
    }
}
