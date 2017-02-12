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
     * Speed controller for the motor that is used to climb up rope. This might
     * control two motors someday.
     */
    public static Victor climberSpeedController = new Victor(0); //TODO: get real port

    public static Victor shooterRollerSpeedController = new Victor(1); //TODO: get real port
    
    public static Victor conveyorSpeedControllerOne = new Victor(2); //TODO: get real port
    public static Victor conveyorSpeedControllerTwo = new Victor(3); //TODO: get real port
    
    /**
     * Speed controller for right side of the robot.
     */
    public static Victor leftDrivepodSpeedController = new Victor(4);
    
    /**
     * Speed controller for the left side of the robot.
     */
    public static Victor rightDrivepodSpeedController = new Victor(5);
    
    // VICTOR #6 would go here
    
    /**
     * Speed controller for the roller at the bottom of the robot that sucks
     * fuel balls in.
     */
    public static Victor ballIntakeRollerSpeedController = new Victor(7); //TODO: get real port
    
    // VICTOR #8 would go here
    
    // VICTOR #9 would go here

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
    public static final int climberPdpPort = 1;
    
    public class SmartDashboardKey {
    	public static final String kCameraExposure = "Camera exposure";
    	public static final String kVideoFeedResolution = "Video feed resolution";
    }
}
