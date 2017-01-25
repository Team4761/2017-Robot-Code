package org.robockets.steamworks.commands;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.subsystems.Climber;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {

	private double time;
	private double speed;
	private boolean climbWithTime = false; // Determines isFinished. Will the command use time?
	
	/**
	 * Climb a rope until the motor stalls signaling hitting the button.
	 * @param speed	The speed to climb the rope in.
	 */
	public Climb (double speed) {
		requires(Robot.climber);
		this.speed = speed;
	}
	
	/**
	 * Climb a rope given a certain time interval.
	 * @param time The time, in seconds, to stall the motor for. Set the time to zero to run it indefinitely.
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
	}

	@Override
	protected void execute() {
		Robot.climber.setMotor(speed);
	}

	protected boolean isFinished() {
		if (climbWithTime && time != 0) { // The case that the command runs within a certain time.
			return isTimedOut(); 
		} else if (!climbWithTime) { // The case that the command runs until the motor stalls.
			return Robot.climber.readCurrent() > Climber.STALLING_THRESHOLD;
		}									
		return false; // The case that the command runs indefinitely.
	}
	
	protected void interrupted() {
		end();
	}
	
	protected void end() {
		Robot.climber.setMotor(0); // Brake the motor.
	}

}
