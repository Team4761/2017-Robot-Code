package org.robockets.steamworks.intakeflap;

import edu.wpi.first.wpilibj.command.Command;

import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class IntakeToPos extends Command {

	private IntakeFlap.IntakeState state;

	public IntakeToPos(IntakeFlap.IntakeState state) {
		this.state = state;
	}

	protected void initialize() {
		Robot.intakeFlap.setState(state);
		if(state == IntakeFlap.IntakeState.GEARS) {
			System.out.println("Fuel!!!!");
			Robot.ledSubsystem.cylon(4); // Green
			Robot.intakeFlap.setPosition(RobotMap.INTAKE_FLAP_FUEL_LEFT_POS, RobotMap.INTAKE_FLAP_FUEL_RIGHT_POS);
		} else {
			System.out.println("Gears!!!!");
			Robot.ledSubsystem.cylon(2); // Yellow
			Robot.intakeFlap.setPosition(RobotMap.INTAKE_FLAP_GEARS_LEFT_POS, RobotMap.INTAKE_FLAP_GEARS_RIGHT_POS);
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
