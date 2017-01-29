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
    private final EncoderPIDSource encoderPIDSource;

    public final PIDController gyroPID;
    public final PIDController encoderPID;

    public Drivetrain() {
        gyroPIDSource = new GyroPIDSource();
        gyroPID = new PIDController(0, 0, 0, new GyroPIDSource(), new DummyPIDOutput());

        gyroPID.disable();
        gyroPID.setOutputRange(-1.0, 1.0); // Set turning speed range
        gyroPID.setPercentTolerance(5.0); // Set tolerance of 5%
        gyroPID.setSetpoint(0);
        
        encoderPIDSource = new EncoderPIDSource(RobotMap.leftEncoder, 1.0 / 1.0); // Encoder factor: 1 / ticks per inch
        encoderPID = new PIDController(0, 0, 0, encoderPIDSource, new DummyPIDOutput());
        
        encoderPID.disable();
        encoderPID.setOutputRange(-1.0, 1.0);
    }

    public void initDefaultCommand() {
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
        disableEncoderPID();
    }
    
    public void driveDistance(double distance) {
    	setDistanceInInches(distance);
    	RobotMap.robotDrive.arcadeDrive(encoderPID.get(), 0);
    }
    
    /**
     * A basic method to set the setpoint of the robot for a given distance with PID
     * @param distance Desired distance, in inches
     */
    public void setDistanceInInches(double distance) {
    	encoderPID.setSetpoint(distance); // This is wrong, find encoder ticks per inch and edit the parameter on EncoderPIDSource!
    	encoderPID.enable();
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
    	encoderPID.reset();
    }

}

