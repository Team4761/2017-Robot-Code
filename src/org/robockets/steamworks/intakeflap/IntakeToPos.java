package org.robockets.steamworks.intakeflap;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

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
		if(state == IntakeFlap.IntakeState.GEARS) {
			Robot.intakeFlap.setPosition(RobotMap.INTAKE_FLAP_FUEL__LEFT_POS, RobotMap.INTAKE_FLAP_FUEL__RIGHT_POS);
		} else {
			Robot.intakeFlap.setPosition(RobotMap.INTAKE_FLAP_GEARS_LEFT_POS, RobotMap.INTAKE_FLAP_GEARS__RIGHT_POS);
		}
	}

	protected void execute() {
		//Robot.intakeFlap.start();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
		//Robot.intakeFlap.stop();
	}

	protected void interrupted() {
		end();
	}
}
