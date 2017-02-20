package org.robockets.steamworks.intakeflap;

import edu.wpi.first.wpilibj.command.Command;
import org.robockets.steamworks.Robot;

/**
 * @author Jake Backer
 */
public class IntakeToPos extends Command {

	IntakeFlap.IntakeState state;

	public IntakeToPos(IntakeFlap.IntakeState state) {
		this.state = state;
	}

	protected void initialize() {
		Robot.intakeFlap.setState(state);
		//setTimeout(2);// Time to move
		if(state == IntakeFlap.IntakeState.FUEL) {
			Robot.intakeFlap.setPosition(0);
			
		} else {
			Robot.intakeFlap.setPosition(45);
		}
	}

	protected void execute() {
		//Robot.intakeFlap.start();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
		Robot.intakeFlap.stop();
	}

	protected void interrupted() {
		end();
	}
}
