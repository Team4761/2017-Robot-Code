package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
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
        
        leftPodPIDSource = new EncoderPIDSource(RobotMap.leftEncoder, 1.0 / 1.0); // Encoder factor: 1 / ticks per inch
        leftPodPID = new PIDController(0, 0, 0, leftPodPIDSource, new DummyPIDOutput());
        leftPodPID.disable();
        leftPodPID.setOutputRange(-1.0, 1.0);
        
        rightPodPIDSource = new EncoderPIDSource(RobotMap.rightEncoder, 1.0 / 1.0);
        rightPodPID = new PIDController(0, 0, 0, rightPodPIDSource, new DummyPIDOutput());
        rightPodPID.disable();
        rightPodPID.setOutputRange(-1.0, 1.0);
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
    
    public void driveDistance(double distance) {
    	setDistanceInInches(distance);
    	RobotMap.robotDrive.arcadeDrive(leftPodPID.get(), 0);
    }
    
    /**
     * A basic method to set the setpoint of the robot for a given distance with PID
     * @param distance Desired distance, in inches
     */
    public void setDistanceInInches(double distance) {
    	leftPodPID.setSetpoint(distance); // This is wrong, find encoder ticks per inch and edit the parameter on EncoderPIDSource!
    	rightPodPID.setSetpoint(distance);

        leftPodPID.enable();
    	rightPodPID.enable();
    }

    public void absoluteTurn(double angle) {
        gyroPID.setSetpoint(angle);
        gyroPID.enable();
    }

    public void relativeTurn(double angle) {
        double newAngle = gyroPIDSource.pidGet() + angle;
        gyroPID.setSetpoint(newAngle);
        gyroPID.enable();
    }

    /**
     * Turn on PID turning (THIS IS ONLY FOR TESTING!)
     */
    public void pidGo() {
        RobotMap.robotDrive.arcadeDrive(0, gyroPID.get());
    }
    
    /**
     * A method to stop the drivetrain
     */
    public void stop() {
        driveArcade(0,0);
        gyroPID.disable();
    }
    
    public void disableEncoderPID() {
    	leftPodPID.reset();
    }

}

