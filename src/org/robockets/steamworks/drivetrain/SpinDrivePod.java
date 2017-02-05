package org.robockets.steamworks.drivetrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Jake Backer
 */
public class SpinDrivePod extends Command {

	PIDController controller;
	DriveDistanceProf ddp;

	public SpinDrivePod(PIDController controller, DriveDistanceProf ddp, double distance, double velocity) {
		this.controller = controller;
		this.ddp = ddp;
		updateParameters(distance, velocity, this.controller.getSetpoint());
	}

	public void updateParameters(double distance, double velocity, double currentPosition) {
		ddp.updateParameters(distance + currentPosition, velocity, currentPosition);
	}
	
	protected void initialize() {
		controller.enable();
	}

	protected void execute() {
		controller.setSetpoint(ddp.getNewPosition());
		SmartDashboard.putNumber("SpinDrivePodSetpoint", controller.getSetpoint());
		//SmartDashboard.putBoolean("ControllerOnTarget", controller.onTarget());
	}

	protected boolean isFinished() {
		boolean isOnTarget = compareInRange(controller.getSetpoint(), ddp.getTargetPosition(), 0.5);
		SmartDashboard.putBoolean("ControllerOnTarget", isOnTarget);

		SmartDashboard.putBoolean("IsInPosition", ddp.isInPosition());

		return ddp.isInPosition() && isOnTarget;
	}

	private boolean compareInRange(double a, double b, double delta) {
		return Math.abs(a - b) <= Math.abs(delta);
	}

	protected void end() {
		controller.disable();
	}

	protected void interrupted() {
		end();
	}

}
