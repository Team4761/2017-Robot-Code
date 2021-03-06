package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.RobotMap;
import org.robockets.steamworks.pidoutput.GyroPIDOutput;
import org.robockets.steamworks.pidsources.EncoderPIDSource;
import org.robockets.steamworks.pidsources.GyroPIDSource;

/**
 * @author Jake Backer & Brian Shin
 * Drivetrain subsystem
 */
public class Drivetrain extends Subsystem {

    public static final double CENTERPOINT_TO_WHEEL = 14.5; // In inches, of course
    private final GyroPIDSource gyroPIDSource;
    private final EncoderPIDSource leftPodPIDSource;
    private final EncoderPIDSource rightPodPIDSource;

    public final PIDController gyroPID;
    public final PIDController leftPodPID;
    public final PIDController rightPodPID;

    public Drivetrain() {
        gyroPIDSource = new GyroPIDSource();
        gyroPID = new PIDController(0, 0, 0, new GyroPIDSource(), new GyroPIDOutput()); // FIXME: This should be initialized in RobotMap
        gyroPID.disable();
        gyroPID.setOutputRange(-1.0, 1.0); // Set turning speed range
        gyroPID.setPercentTolerance(5.0); // Set tolerance of 5%
        
        leftPodPIDSource = new EncoderPIDSource(RobotMap.leftEncoder, 1); // This should be 1
		leftPodPID = new PIDController(0.06, 0.01, 0.25, leftPodPIDSource, RobotMap.leftDrivePodOutput); //NOTE: Even with 0.1 P it is still going VERY fast and uneven relative to the right
        leftPodPID.disable();
        leftPodPID.setOutputRange(-1.0, 1.0);
        leftPodPID.setAbsoluteTolerance(0.5);
        
        rightPodPIDSource = new EncoderPIDSource(RobotMap.rightEncoder, 1);
        rightPodPID = new PIDController(-0.06, -0.01, -0.25, rightPodPIDSource, RobotMap.rightDrivePodOutput);
        rightPodPID.disable();
        rightPodPID.setOutputRange(-1.0, 1.0);
        rightPodPID.setAbsoluteTolerance(0.5);
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new Joyride());
	}

    /**
     * Basic method to control the movement of the robot 'arcade' style
     * @param translate Motor speed to move forward: -1 to 1
     * @param rotate Rotation speed: -1 to 1
     */
    public void driveArcade(double translate, double rotate) {
        RobotMap.robotDrive.arcadeDrive(translate, rotate);
        disableEncoderPID();
    }

    /**
     * Basic method to control the movement of the robot 'tank' style
     * @param leftValue Motor speed for the left side of the robot: -1 to 1
     * @param rightValue Motor speed for the right side of the robot: -1 to 1
     */
    public void driveTank(double leftValue, double rightValue) {
        RobotMap.robotDrive.tankDrive(leftValue, rightValue);
    }
    
    /**
     * A method to drive along an arc with PID, its usefulness is debatable
     * @param direction Direction of the turn, left or right
     * @param chordLength Length of the hypotenuse of the triangle formed by two radii
     * @param radius Distance between the robot and a point directly below the target
     */
    public void driveArc(RelativeDirection.XAxis direction, double chordLength, double radius) {
    	double arcLengthLeft;
    	double arcLengthRight;
    	if(direction == RelativeDirection.XAxis.RIGHT) {
    		arcLengthLeft = calculateArcLength(chordLength, radius + CENTERPOINT_TO_WHEEL);
    		arcLengthRight = calculateArcLength(chordLength, radius - CENTERPOINT_TO_WHEEL);
    	} else {
    		arcLengthLeft = calculateArcLength(chordLength, radius - CENTERPOINT_TO_WHEEL);
    		arcLengthRight = calculateArcLength(chordLength, radius + CENTERPOINT_TO_WHEEL);
    	}
    	
    	setDistance(arcLengthLeft, arcLengthRight);
    }
    
    /**
     * A basic method to set the setpoint of both of the pods for a given distance with PID
     * @param distance Desired distance for both pods, in inches
     */
    public void setDistance(double distance) {
    	rightPodPID.setSetpoint(distance);
    	leftPodPID.setSetpoint(distance);
    }
    
    /**
     * A basic method to set the setpoint of each of the pods for a given distance with PID
     * @param leftDistance Desired distance for the left pod, in inches
     * @param rightDistance Desired distance for the right pod, in inches
     */
    public void setDistance(double leftDistance, double rightDistance) {
    	leftPodPID.setSetpoint(leftDistance);
    	rightPodPID.setSetpoint(rightDistance);
    }
    
    /**
     * Method for calculating arc length
     * @param chordLength Distance from point A to B on the circumference of a circle
     * @param radius Radius of the circle
     * @return Returns the arc length, in inches
     */
    private double calculateArcLength(double chordLength, double radius) {
    	return Math.toRadians(Math.asin(chordLength / radius/ 2)) * 96;
    }
    
    /**
     * Since the built in OnTarget for PID is terrible and broken, this is a manual one for gyro
     * @return Returns if the gyro PID is OnTarget, with a tolerance of <code>PERCENT_TOLERANCE</code>
     */
    public boolean gyroOnTarget() {
    	final double PERCENT_TOLERANCE = 5.0;
    	return Math.abs((gyroPID.getSetpoint() - gyroPIDSource.pidGet()) / gyroPID.getSetpoint()) <= PERCENT_TOLERANCE;
    }
    
    /**
     * Since the built in OnTarget for PID is terrible and broken, this is a manual one for the drive pods
     * @return Returns if both the encoder PIDs are OnTarget, with a tolerance of <code>PERCENT_TOLERANCE</code>
     */
   public boolean encodersOnTarget() {  
  	final double ABSOLUTE_TOLERANCE = 1.0;
   	return Math.abs((leftPodPID.getSetpoint() - leftPodPIDSource.pidGet())) <= ABSOLUTE_TOLERANCE &&
   			Math.abs((rightPodPID.getSetpoint() - rightPodPIDSource.pidGet())) <= ABSOLUTE_TOLERANCE; // michael did this
    }
    
    /**
     * Method to enable both drive pod PIDs
     */
    public void enableEncoderPID() {
    	leftPodPID.enable();
    	rightPodPID.enable();
    }
    
    /**
     * A method to disable the encoder PIDs
     */
    public void disableEncoderPID() {
    	leftPodPID.reset();
    	rightPodPID.reset();
    }
    
    /**
     * A method to stop the drivetrain
     */
    public void stop() {
        driveArcade(0,0);
        gyroPID.disable();
    }
    
	public void resetEncoders() {
		RobotMap.leftEncoder.reset();
		RobotMap.rightEncoder.reset();
	}

	public double getAverageCurrent() {
		double drivetrainCurrentSum = RobotMap.powerDistPanel.getCurrent(12) + RobotMap.powerDistPanel.getCurrent(3) + RobotMap.powerDistPanel.getCurrent(13) + RobotMap.powerDistPanel.getCurrent(0);
		double drivetrainCurrentAvg = drivetrainCurrentSum / 4;
		return drivetrainCurrentAvg;
	}
}

