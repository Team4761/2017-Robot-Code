package org.robockets.steamworks.intakeflap;

import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeFlap extends Subsystem {

	private double speed;
	private IntakeState lastState;

    public void initDefaultCommand() {
	}
    
    public IntakeFlap(double speed) {
    	super();
    	setSpeed(speed);
    	this.lastState = IntakeState.GEARS;
    }

    public void setState(IntakeState state) {
    	lastState = state;
	}

    /**
     * Set the speed with which the servo moves.
     * @param speed Speed on a scale of 0...1.
     * @throws IllegalArgumentException When speed is not in the correct range.
     */
    public void setSpeed(double speed) throws IllegalArgumentException {
    	if(speed > 1.0 || speed < 0) throw new IllegalArgumentException("Servo speed not in range 0...1");
    	this.speed = speed / 2; // This gets added onto the "stopped" speed of 0.5. The scale for the arguments is just 0...1 to be familiar
    }
    
    public void setPosition(double leftPos, double rightPos) {
    	RobotMap.leftIntakeFlapServo.set(leftPos);
    	RobotMap.rightIntakeFlapServo.set(rightPos);
    }


	public void toggle() {
    	if (lastState == IntakeState.GEARS) {
    		setPosition(RobotMap.INTAKE_FLAP_FUEL_LEFT_POS, RobotMap.INTAKE_FLAP_FUEL_RIGHT_POS);
    		setState(IntakeState.FUEL);
		} else {
    		setPosition(RobotMap.INTAKE_FLAP_GEARS_LEFT_POS, RobotMap.INTAKE_FLAP_GEARS_RIGHT_POS);
    		setState(IntakeState.GEARS);
		}
	}
    
    public enum IntakeState {
    	GEARS(1),
    	FUEL(-1);
    	public final int factor;

    	IntakeState(int factor) {
    		this.factor = factor;
    	}
    }
}

