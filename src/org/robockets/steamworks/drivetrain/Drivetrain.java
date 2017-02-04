package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.DummyPIDOutput;
import org.robockets.steamworks.RobotMap;
import org.robockets.steamworks.pidsources.EncoderPIDSource;
import org.robockets.steamworks.pidsources.GyroPIDSource;

/**
 * @author Jake Backer & Brian Shin
 * Drivetrain subsystem
 */
public class Drivetrain extends Subsystem {

    private final GyroPIDSource gyroPIDSource;
    private final EncoderPIDSource leftPodPIDSource;
    private final EncoderPIDSource rightPodPIDSource;
    private final double CENTERPOINT_TO_WHEEL = 0; // In inches, of course

    public final PIDController gyroPID;
    public final PIDController leftPodPID;
    public final PIDController rightPodPID;

    public Drivetrain() {
        gyroPIDSource = new GyroPIDSource();
        gyroPID = new PIDController(0, 0, 0, new GyroPIDSource(), new DummyPIDOutput());

        gyroPID.disable();
        gyroPID.setOutputRange(-1.0, 1.0); // Set turning speed range
        gyroPID.setPercentTolerance(5.0); // Set tolerance of 5%
        gyroPID.setSetpoint(0);
        
        leftPodPIDSource = new EncoderPIDSource(RobotMap.leftEncoder, 0.05555); // Encoder factor: 1 / ticks per inch
        leftPodPID = new PIDController(-0.1, 0, 0, leftPodPIDSource, RobotMap.leftDrivePodOutput);
        leftPodPID.disable();
        leftPodPID.setOutputRange(-1.0, 1.0);
        leftPodPID.setAbsoluteTolerance(0.5);
        
        rightPodPIDSource = new EncoderPIDSource(RobotMap.rightEncoder, 0.05555);
        rightPodPID = new PIDController(0, 0, 0, rightPodPIDSource, RobotMap.rightDrivePodOutput);
        rightPodPID.disable();
        rightPodPID.setOutputRange(-1.0, 1.0);
        rightPodPID.setAbsoluteTolerance(0.5);
    }

    public void initDefaultCommand() {}

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
        disableEncoderPID();
    }
    
    /**
     * Method to control the driving of the robot through gyro or encoder PID; does not set setpoint of any PID; use the other methods.
     * @param moveValue Constant speed to be driving the robot; input 0 if using encoderPID
     * @param gyro Whether or not to use gyroPID or not
     * @param encoder Whether or not to use encoderPID or not
     */
    public void driveAssisted(double moveValue, boolean gyro, boolean encoder) {
    	if(gyro && !encoder) {
    		driveArcade(moveValue, gyroPID.get());
    	} else if(encoder && !gyro) {
    		driveTank(leftPodPID.get(), rightPodPID.get());
    	}
    }
    
    /**
     * A method to drive straight for a certain distance, based on leftPodPID
     * @param distance Distance, in inches
     */
    public void driveDistance(double distance, double velocity) {

    }
    
    /**
     * A method to drive along an arc with PID, its usefulness is debatable
     * @param direction Direction of the turn, left or right
     * @param chordLength Length of the hypotenuse of the triangle formed by two radii
     * @param radius Distance between the robot and a point directly below the target
     * @param scalar Speed scalar
     */
    public void driveArc(RelativeDirection.XAxis direction, double chordLength, double radius, double scalar) {
    	double arcLengthLeft;
    	double arcLengthRight;
    	if(direction == RelativeDirection.XAxis.RIGHT) {
    		arcLengthLeft = calculateArcLength(chordLength, radius + CENTERPOINT_TO_WHEEL);
    		arcLengthRight = calculateArcLength(chordLength, radius - CENTERPOINT_TO_WHEEL);
    	} else {
    		arcLengthLeft = calculateArcLength(chordLength, radius - CENTERPOINT_TO_WHEEL);
    		arcLengthRight = calculateArcLength(chordLength, radius + CENTERPOINT_TO_WHEEL);
    	}
    	
    	setDistanceInInches(arcLengthLeft, arcLengthRight);
    	driveTank(leftPodPID.get() * scalar, rightPodPID.get() * scalar);
    }
    
    /**
     * A basic method to set the setpoint of both of the pods for a given distance with PID
     * @param distance Desired distance for both pods, in inches
     */
    public void setDistanceInInches(double distance) {
    	leftPodPID.setSetpoint(distance); // This is wrong, find encoder ticks per inch and edit the parameter on EncoderPIDSource!
    	rightPodPID.setSetpoint(distance);

        leftPodPID.enable();
    	rightPodPID.enable();
    }
    
    /**
     * A basic method to set the setpoint of each of the pods for a given distance with PID
     * @param leftDistance Desired distance for the left pod, in inches
     * @param rightDistance Desired distance for the right pod, in inches
     */
    public void setDistanceInInches(double leftDistance, double rightDistance) {
    	leftPodPID.setSetpoint(leftDistance);
    	rightPodPID.setSetpoint(rightDistance);
    	
    	leftPodPID.enable();
    	rightPodPID.enable();
    }
    
    /**
     * Method to turn the robot in place relative to where 0 degrees is
     * @param angle Desired angle in degrees
     */
    public void absoluteTurn(double angle) {
        gyroPID.setSetpoint(angle);
        gyroPID.enable();
        driveTank(gyroPID.get(), -gyroPID.get());
    }

    /**
     * A method to turn the robot in place relative to current position
     * @param angle Desired angle in degrees
     */
    public void relativeTurn(double angle) {
        double newAngle = gyroPIDSource.pidGet() + angle;
        gyroPID.setSetpoint(newAngle);
        gyroPID.enable();
        driveTank(gyroPID.get(), -gyroPID.get());
    }

    /**
     * Turn on PID turning (THIS IS ONLY FOR TESTING!)
     */
    public void pidGo() {
        leftPodPID.enable();
    }
    
    /**
     * Method for calculating arc length
     * @param chordLength Distance from point A to B on the circumference of a circle
     * @param radius Radius of the circle
     * @return Returns the arc length, in inches
     */
    public double calculateArcLength(double chordLength, double radius) {
    	return Math.toRadians(Math.asin(chordLength / radius/ 2)) * 96;
    }
    
    /**
     * Method that checks whether the encoder PIDs are enabled or not
     * @return Returns true or false
     */
    public boolean isEncoderPIDEnabled() {
    	if(leftPodPID.isEnabled() && rightPodPID.isEnabled()) {
    		return true;
    	}
    	return false;
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
}

