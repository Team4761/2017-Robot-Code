package org.robockets.steamworks.shooter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.robockets.steamworks.CounterRatePIDSource;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class Shooter extends Subsystem {

    private final double ROLLER_SPEED = -0.5;
    
    private CounterRatePIDSource counterPIDSource = new CounterRatePIDSource(RobotMap.rollerEncoderCounter);
    
    public PIDController shooterPIDController;

    public Shooter() {
    	shooterPIDController = new PIDController(0, 0, 0, 0, counterPIDSource, RobotMap.shooterRollerSpeedController);
    	shooterPIDController.disable();
    	shooterPIDController.setOutputRange(0, 1);
    	shooterPIDController.setPercentTolerance(5);
    }
   
    public void initDefaultCommand() {

    }

    /**
     * Spins up the motors to shoot
     * @deprecated
     */
    @Deprecated
    public void spinUp() {
        RobotMap.shooterRollerSpeedController.set(ROLLER_SPEED);
    }
    
    public void spinUp(double rollerSpeed) {
    	RobotMap.shooterRollerSpeedController.set(rollerSpeed);
    }

    public void enablePID() {
    	shooterPIDController.setSetpoint(SmartDashboard.getNumber("Shooter PID setpoint", 0));
    	shooterPIDController.enable();
	}


    public void stop() {
        RobotMap.shooterRollerSpeedController.stopMotor();
    }
}

