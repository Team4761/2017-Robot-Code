package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Jake Backer
 */
public class SpinDrivePod extends Command {

	PIDController controller;
	DriveDistanceProf ddp;

	public SpinDrivePod(PIDController controller, DriveDistanceProf ddp, double distance, double velocity) {
		this.controller = controller;
		this.ddp = ddp;
		updateParameters(distance, velocity, 0);
	}

	public void updateParameters(double distance, double velocity, double currentPosition) {
		ddp.updateParameters(distance + currentPosition, velocity, currentPosition);
	}
	
	protected void initialize() {
		controller.enable();
	}

	protected void execute() {
		controller.setSetpoint(ddp.getNewPosition());
	}

	protected boolean isFinished() {
		return ddp.isInPosition() && controller.onTarget();
	}

	protected void end() {
		controller.disable();
	}

	protected void interrupted() {
		end();
	}
}
