package org.robockets.steamworks.climber;

import org.robockets.steamworks.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {

	private double time;
	private double speed;
	private boolean climbWithTime = false; // Determines isFinished. Will the command use time?
	
	/**
	 * Climb a rope until the motor stalls signaling hitting the button.
	 * @param speed		The speed to climb the rope in. 0 through 1
	 */
	public Climb (double speed) {
		requires(Robot.climber);
		this.speed = speed;
	}
	
	/**
	 * Climb a rope given a certain time interval.
	 * @param speed		The speed to climb the rope in. 0 through 1
	 * @param time		The time, in seconds, to run the motor for. Set the time to zero to run it indefinitely.
	 */
	public Climb (double speed, double time) {
		this(speed);
		this.time = time;
		climbWithTime = true;
	}
	
	protected void initialize() {
		if (climbWithTime == true && time != 0) {
			setTimeout(time);
		}
		Robot.ledSubsystem.cylon(2);
	}

	@Override
	protected void execute() {
		Robot.climber.setMotor(speed);
	}

	protected boolean isFinished() {
		if (climbWithTime && time != 0) { // The case that the command runs within a certain time.
			return isTimedOut(); 
		} else if (!climbWithTime) { // The case that the command runs until the motor stalls.
			return Robot.climber.readCurrent() > Robot.climber.STALLING_THRESHOLD;
		}									
		return false; // The case that the command runs indefinitely.
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		Robot.climber.stop(); // Brake the motor.
		Robot.ledSubsystem.cylon(56);
	}

}
