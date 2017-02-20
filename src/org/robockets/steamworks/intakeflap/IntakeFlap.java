package org.robockets.steamworks.intakeflap;

import org.robockets.steamworks.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeFlap extends Subsystem {
    public void initDefaultCommand() {}
    private double speed;
    private IntakeState lastState;
    
    public IntakeFlap(double speed) {
    	super();
    	setSpeed(speed);
    	this.lastState = IntakeState.GEARS; //Confirmed as starting position by Jake K
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
    
    public void setPosition(double pos) {
    	RobotMap.intakeFlapServo1.setAngle(pos);
    	RobotMap.intakeFlapServo2.setAngle(180 - pos);
    }

    @Deprecated
    public void start() {
    	RobotMap.intakeFlapServo1.set(1 + (speed * lastState.factor));
    }

    public void move(double speed) {
		RobotMap.intakeFlapServo1.set(speed);
		RobotMap.intakeFlapServo1.set(180 - speed);
	}

	public void toggle() {
    	if (lastState == IntakeState.GEARS) {
    		setPosition(45);
    		setState(IntakeState.FUEL);
		} else {
    		setPosition(0);
    		setState(IntakeState.GEARS);
		}
	}

	@Deprecated
    public void stop() {
    	RobotMap.intakeFlapServo1.set(0);
    	if(lastState == IntakeState.FUEL) {
    		lastState = IntakeState.GEARS;
		} else {
			lastState = IntakeState.FUEL;
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

