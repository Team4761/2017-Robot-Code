package org.robockets.steamworks.camera;

import edu.wpi.first.wpilibj.command.Command;

public class SetVisionEnabled extends Command {

	private boolean value;

	public SetVisionEnabled(boolean value) {
		this.value = value;
		setRunWhenDisabled(true);
	}
	@Override
	protected void execute() {
		CVConstants.SHOULD_RUN_VISION = value;
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
