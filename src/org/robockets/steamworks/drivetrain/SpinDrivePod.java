package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import org.robockets.commons.RelativeDirection;
import org.robockets.steamworks.Robot;
import org.robockets.steamworks.RobotMap;

/**
 * @author Jake Backer
 */
public class SpinDrivePod extends Command {

	PIDController controller;
	DriveDistanceProf ddp;
	boolean isFinishable;

	public SpinDrivePod(PIDController controller, DriveDistanceProf ddp, double distance, double velocity, boolean isFinishable) {
		this.controller = controller;
		this.ddp = ddp;
		this.isFinishable = isFinishable;
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
		return this.isFinishable && ddp.isInPosition() && controller.onTarget();
	}

	protected void end() {
		controller.disable();
	}

	protected void interrupted() {
		end();
	}
}
