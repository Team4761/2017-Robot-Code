package org.robockets.steamworks;

import edu.wpi.first.wpilibj.*;

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
	
	/**
	 * Speed controller for the front left wheel of the robot.
	 */
    public static Talon frontLeftSpeedController = new Talon(0);
    
    /**
     * Speed controller for the back left wheel of the robot.
     */
    public static Talon backLeftSpeedController = new Talon(1);
    
    /**
     * Speed controller for the front right wheel of the robot.
     */
    public static Talon frontRightSpeedController = new Talon(2);
    
    /**
     * Speed controller for the back right wheel of the robot.
     */
    public static Talon backRightSpeedController = new Talon(3);
    
    /**
     * Speed controller for the motor that is used to climb up rope. This might
     * control two motors someday.
     */
    public static Talon climberSpeedController = new Talon(4);
    
    //TODO: JavaDocs for these four.
    public static TalonSRX conveyorSpeedControllerOne = new TalonSRX(5);
    public static TalonSRX conveyorSpeedControllerTwo = new TalonSRX(6);
    public static TalonSRX ballIntakeRollerSpeedController = new TalonSRX(7);
	public static TalonSRX shooterRollerSpeedController = new TalonSRX(8);

	//////////////////
	/// Digital IO ///
	//////////////////
	
	/**
	 * Encoder that goes on the left drivepod. For getting how fast the left
	 * side of the robot is driving.
	 */
	public static Encoder leftEncoder = new Encoder(1, 0);
	
	/**
	 * Encoder that goes on the right drivepod. For getting how fast the right
	 * side of the robot is driving.
	 */
	public static Encoder rightEncoder = new Encoder(2, 3);
	
    //public static DigitalInput gearInputBreakbeamSensor = new DigitalInput(2);
	
	/**
	 * Encoder that goes on the shooter roller. For getting how fast the
	 * shooter roller is spinning.
	 */
    public static Encoder rollerEncoder = new Encoder(8, 9);


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
    
    /**
     * {@link edu.wpi.first.wpilibj.RobotDrive RobotDrive} for controlling the
     * the four speed controllers on our two drivepods. Used in teleoperated
     * mode.
     */
    public static RobotDrive robotDrive = new RobotDrive(frontLeftSpeedController, backLeftSpeedController, frontRightSpeedController, backRightSpeedController);
    
    /**
     * {@link edu.wpi.first.wpilibj.PIDOutput PIDOutput} for controlling the
     * left drivepod.
     */
    public static DrivePodOutput leftDrivePodOutput = new DrivePodOutput(frontLeftSpeedController, backLeftSpeedController);
    
    /**
     * {@link edu.wpi.first.wpilibj.PIDOutput PIDOutput} for controlling the
     * right drivepod.
     */
    public static DrivePodOutput rightDrivePodOutput = new DrivePodOutput(frontRightSpeedController, backRightSpeedController);
    
    /**
     * The power distribution panel. Used for getting current to the climber
     * motor, letting us know when it is stalling.
     */
    public static PowerDistributionPanel powerDistPanel = new PowerDistributionPanel(); // Please note that this must be CAN id 0.
    
    /**
     * PDP port that the climber's motor is attached to.
     */
    public static final int climberPdpPort = 1;
    
    public class SmartDashboardKey {
    	public static final String kCameraExposure = "Camera exposure";
    	public static final String kVideoFeedResolution = "Video feed resolution";
    }
}
